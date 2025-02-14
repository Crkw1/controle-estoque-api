package br.com.controleestoqueapi.users.application.usercase;

import br.com.controleestoqueapi.users.application.dto.UpdateUserRequest;
import br.com.controleestoqueapi.users.application.dto.UserResponse;
import br.com.controleestoqueapi.users.domain.exception.UserNotFoundException;
import br.com.controleestoqueapi.users.domain.model.User;
import br.com.controleestoqueapi.users.domain.model.UserId;
import br.com.controleestoqueapi.users.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateUserUseCase {

    private final UserRepository userRepository;

    public UpdateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponse execute(UserId userId, UpdateUserRequest request) {
        // 1. Buscar o usuário existente.  Se não existir, lança exceção.
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com ID: " + userId.value()));

        // 2. Atualizar os campos *permitidos* (NÃO atualize a senha aqui!)
        User updatedUser = new User( //Usa o construtor completo, pois o usuário já existe
                user.getId(), //Mantem o id
                request.name() != null ? request.name() : user.getName(), //Mantem o valor atual se for null
                request.email() != null ? request.email() : user.getEmail(), //Mantem o valor atual se for null
                user.getPassword(),  // Mantém a senha *original* (criptografada). Update de senha em outro caso de uso!
                request.phoneNumber() != null ? request.phoneNumber() : user.getPhoneNumber(), //Mantem o valor atual se for null
                request.address() != null ? request.address() : user.getAddress(), //Mantem o valor atual se for null
                request.roles() != null ? request.roles() : user.getRoles()
        );
        // 3. Salvar as mudanças.
        User savedUser = userRepository.save(updatedUser);

        // 4. Retornar a resposta (DTO).
        return UserResponse.fromDomain(savedUser);
    }
}