package br.com.controleestoqueapi.users.infrastructure.controller;

import br.com.controleestoqueapi.users.application.dto.CreateUserRequest;
import br.com.controleestoqueapi.users.application.dto.UpdateUserRequest;
import br.com.controleestoqueapi.users.application.dto.UserResponse;
import br.com.controleestoqueapi.users.application.usercase.*;
import br.com.controleestoqueapi.users.domain.model.UserId;
import br.com.controleestoqueapi.users.infrastructure.dto.UserRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final ListUsersUseCase listUsersUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    @Autowired
    public UserController(CreateUserUseCase createUserUseCase, GetUserByIdUseCase getUserByIdUseCase, ListUsersUseCase listUsersUseCase, UpdateUserUseCase updateUserUseCase, DeleteUserUseCase deleteUserUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.listUsersUseCase = listUsersUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest request) {
        CreateUserRequest useCaseRequest = new CreateUserRequest(
                request.name(),
                request.email(),
                request.password(),
                request.phoneNumber(),
                request.address(),
                request.roles()
        );
        UserResponse response = createUserUseCase.execute(useCaseRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUsersById(@PathVariable Long id){
        return ResponseEntity.ok(getUserByIdUseCase.execute(new UserId(id)));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> listUsers() {
        List<UserResponse> users = listUsersUseCase.execute();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody @Valid UpdateUserRequest request) {
        UserResponse updatedUser = updateUserUseCase.execute(new UserId(id), request);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable Long id){
        deleteUserUseCase.execute(new UserId(id));
        return ResponseEntity.noContent().build();
    }
}