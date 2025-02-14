package br.com.controleestoqueapi.users.application.usercase;

import br.com.controleestoqueapi.users.application.dto.UserResponse;
import br.com.controleestoqueapi.users.domain.exception.UserNotFoundException;
import br.com.controleestoqueapi.users.domain.model.UserId;
import br.com.controleestoqueapi.users.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class GetUserByIdUseCase {

    private final UserRepository userRepository;

    public GetUserByIdUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponse execute(UserId id) {
        return userRepository.findById(id)
                .map(UserResponse::fromDomain)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com ID: " + id.value()));
    }
}