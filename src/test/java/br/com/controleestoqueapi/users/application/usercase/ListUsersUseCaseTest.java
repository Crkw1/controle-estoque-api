package br.com.controleestoqueapi.users.application.usercase;

import br.com.controleestoqueapi.users.application.dto.UserResponse;
import br.com.controleestoqueapi.users.domain.model.User;
import br.com.controleestoqueapi.users.domain.model.UserId;
import br.com.controleestoqueapi.users.domain.model.enums.UserRole;
import br.com.controleestoqueapi.users.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListUsersUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ListUsersUseCase listUsersUseCase;

    @Test
    void execute_ShouldReturnListOfUsers() {

        User user1 = new User(new UserId(1L), "User 1", "user1@example.com", "hashedpassword", "123", "Address 1", Set.of(UserRole.USER));
        User user2 = new User(new UserId(2L), "User 2", "user2@example.com", "hashedpassword", "456", "Address 2", Set.of(UserRole.ADMIN));
        List<User> userList = List.of(user1, user2);

        when(userRepository.findAll()).thenReturn(userList);

        List<UserResponse> result = listUsersUseCase.execute();

        assertNotNull(result);
        assertEquals(2, result.size()); // Verifica o tamanho da lista

        assertEquals(1L, result.get(0).id());
        assertEquals("User 1", result.get(0).name());
        assertEquals("user1@example.com", result.get(0).email());

        assertEquals(2L, result.get(1).id());
        assertEquals("User 2", result.get(1).name());
        assertEquals("user2@example.com", result.get(1).email());
    }

    @Test
    void execute_WhenNoUsersExist_ShouldReturnEmptyList() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<UserResponse> result = listUsersUseCase.execute();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}