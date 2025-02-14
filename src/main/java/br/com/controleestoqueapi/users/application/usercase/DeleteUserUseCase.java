package br.com.controleestoqueapi.users.application.usercase;

import br.com.controleestoqueapi.users.domain.exception.UserNotFoundException;
import br.com.controleestoqueapi.users.domain.model.UserId;
import br.com.controleestoqueapi.users.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteUserUseCase {

    private final UserRepository userRepository;

    public DeleteUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void execute(UserId userId) {
        if (!userRepository.findById(userId).isPresent()) {
            throw new UserNotFoundException("Usuário não encontrado com ID: " + userId.value());
        }
        // 2. Excluir o usuário
        userRepository.delete(userId);
    }
}