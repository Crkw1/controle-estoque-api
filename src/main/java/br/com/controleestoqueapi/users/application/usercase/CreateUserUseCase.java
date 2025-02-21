package br.com.controleestoqueapi.users.application.usercase;

import br.com.controleestoqueapi.common.email.EmailService;
import br.com.controleestoqueapi.common.exception.EmailSendingException;
import br.com.controleestoqueapi.users.application.dto.CreateUserRequest;
import br.com.controleestoqueapi.users.application.dto.UserResponse;
import br.com.controleestoqueapi.users.domain.exception.UserAlreadyExistsException;
import br.com.controleestoqueapi.users.domain.model.User;
import br.com.controleestoqueapi.users.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class CreateUserUseCase {

    private static final Logger logger = LoggerFactory.getLogger(CreateUserUseCase.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;


    public CreateUserUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;

    }

    @Transactional
    public UserResponse execute(CreateUserRequest request) {
        logger.info("Criando usuário: {}", request); //LOG
        if (userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException("User with email " + request.email() + " already exists");
        }

        String hashedPassword = passwordEncoder.encode(request.password());

        // Usa o construtor *sem* o ID (pois é um novo usuário)
        User user = new User(
                request.name(),
                request.email(),
                hashedPassword,
                request.phoneNumber(),
                request.address(),
                request.roles() != null ? request.roles(): Set.of()
        );

        User savedUser = userRepository.save(user);

        // Envia o e-mail de confirmação depois de salvar o usuário com sucesso
        try {
            emailService.sendEmail(
                    savedUser.getEmail(),
                    "Confirmação de Cadastro",
                    "Olá " + savedUser.getName() + ",\n\nSua conta foi criada com sucesso!"
            );
        } catch (EmailSendingException e) {
            logger.error("Falha ao enviar e-mail de confirmação para o usuário: {}", savedUser.getEmail(), e);
        }
        logger.info("Usuário criado com sucesso: {}", savedUser); //LOG
        return UserResponse.fromDomain(savedUser);
    }
}