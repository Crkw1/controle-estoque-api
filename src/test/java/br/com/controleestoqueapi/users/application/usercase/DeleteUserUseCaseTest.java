package br.com.controleestoqueapi.users.application.usercase;

import br.com.controleestoqueapi.users.domain.exception.UserNotFoundException;
import br.com.controleestoqueapi.users.domain.model.User;
import br.com.controleestoqueapi.users.domain.model.UserId;
import br.com.controleestoqueapi.users.domain.model.enums.UserRole;
import br.com.controleestoqueapi.users.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DeleteUserUseCase deleteUserUseCase;

    private UserId userId;
    private User user;

    @BeforeEach
    void setUp() {
        userId = new UserId(1L);

        user = new User(userId, "Test User", "test@example.com", "password", "12345", "Some Address", Set.of(UserRole.USER));
    }

    @Test
    void execute_UserExists_DeletesUser() {

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        deleteUserUseCase.execute(userId);

        verify(userRepository).delete(userId);
    }

    @Test
    void execute_UserDoesNotExist_ThrowsException() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            deleteUserUseCase.execute(userId);
        });

        verify(userRepository, never()).delete(any());
    }
}