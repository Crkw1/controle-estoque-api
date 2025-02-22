package br.com.controleestoqueapi.users.application.usercase;

import br.com.controleestoqueapi.users.application.dto.UserResponse;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUserByIdUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserByIdUseCase getUserByIdUseCase;

    private User user;
    private UserId userId;

    @BeforeEach
    void setup() {
        userId = new UserId(1L);
        user = new User(userId, "Test User", "test@example.com", "password", "12345", "Some Address", Set.of(UserRole.USER));

    }

    @Test
    void execute_UserExists_ReturnsUserResponse() {
        // Arrange
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));


        // Act
        UserResponse response = getUserByIdUseCase.execute(userId);

        // Assert
        assertNotNull(response);
        assertEquals(user.getId().value(), response.id());
        assertEquals(user.getName(), response.name());
        assertEquals(user.getEmail(), response.email());

    }

    @Test
    void execute_UserDoesNotExist_ThrowsException() {

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            getUserByIdUseCase.execute(userId);
        });
    }
}