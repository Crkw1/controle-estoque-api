package br.com.controleestoqueapi.users.application.usercase;

import br.com.controleestoqueapi.users.application.dto.UpdateUserRequest;
import br.com.controleestoqueapi.users.application.dto.UserResponse;
import br.com.controleestoqueapi.users.domain.exception.UserNotFoundException;
import br.com.controleestoqueapi.users.domain.model.User;
import br.com.controleestoqueapi.users.domain.model.UserId;
import br.com.controleestoqueapi.users.domain.model.enums.UserRole;
import br.com.controleestoqueapi.users.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UpdateUserUseCase updateUserUseCase;

    private UserId userId;
    private User existingUser;
    private UpdateUserRequest updateRequest;

    @BeforeEach
    void setUp() {
        userId = new UserId(1L);
        existingUser = new User(userId, "Old Name", "old@example.com", "hashed_password", "123", "Old Address", Set.of(UserRole.USER));

        updateRequest = new UpdateUserRequest(
                "New Name",
                "new@example.com",
                "456",
                "New Address",
                Set.of(UserRole.ADMIN)
        );
    }

    @Test
    void execute_WithValidData_ShouldUpdateUser() {
        // Arrange
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(userRepository.save(userCaptor.capture())).thenAnswer(invocation -> userCaptor.getValue());//Retorna o próprio userCaptor.

        UserResponse response = updateUserUseCase.execute(userId, updateRequest);

        assertNotNull(response);
        assertEquals("New Name", response.name());
        assertEquals("new@example.com", response.email());
        assertEquals("456", response.phoneNumber());
        assertEquals("New Address", response.address());
        assertTrue(response.roles().contains(UserRole.ADMIN));

        verify(userRepository).findById(userId);
        verify(userRepository).save(userCaptor.capture());
        User updatedUser = userCaptor.getValue();

        assertEquals("New Name", updatedUser.getName());
        assertEquals("new@example.com", updatedUser.getEmail());
        assertEquals("hashed_password", updatedUser.getPassword());
        assertEquals("456", updatedUser.getPhoneNumber());
        assertEquals("New Address", updatedUser.getAddress());
        assertTrue(updatedUser.getRoles().contains(UserRole.ADMIN));
    }

    @Test
    void execute_UserNotFound_ShouldThrowException() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            updateUserUseCase.execute(userId, updateRequest);
        });

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void execute_WithNullValues_ShouldKeepOriginalValues() {

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        UpdateUserRequest requestWithNulls = new UpdateUserRequest(null, null, null, null, null);
        //Captura o user a ser salvo.
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(userRepository.save(userCaptor.capture())).thenAnswer(invocation -> userCaptor.getValue());//retorna o user capturado

        UserResponse response = updateUserUseCase.execute(userId, requestWithNulls);

        assertNotNull(response);

        assertEquals(existingUser.getName(), response.name());
        assertEquals(existingUser.getEmail(), response.email());
        assertEquals(existingUser.getPhoneNumber(), response.phoneNumber());
        assertEquals(existingUser.getAddress(), response.address());
        assertEquals(existingUser.getRoles(), response.roles());

        verify(userRepository).findById(userId);
        verify(userRepository).save(userCaptor.capture());

        User userSalvo = userCaptor.getValue();
        assertEquals(existingUser.getName(), userSalvo.getName());
        assertEquals(existingUser.getEmail(), userSalvo.getEmail());
        assertEquals(existingUser.getPassword(), userSalvo.getPassword());
        assertEquals(existingUser.getPhoneNumber(), userSalvo.getPhoneNumber());
        assertEquals(existingUser.getAddress(), userSalvo.getAddress());
        assertEquals(existingUser.getRoles(), userSalvo.getRoles());
    }
}