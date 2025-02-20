package br.com.controleestoqueapi.users.infrastructure.controller;

import br.com.controleestoqueapi.users.application.dto.CreateUserRequest;
import br.com.controleestoqueapi.users.application.dto.UpdateUserRequest;
import br.com.controleestoqueapi.users.application.dto.UserResponse;
import br.com.controleestoqueapi.users.application.usercase.*;
import br.com.controleestoqueapi.users.domain.model.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Endpoints para gerenciar usuários")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final ListUsersUseCase listUsersUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    @Autowired
    public UserController(CreateUserUseCase createUserUseCase, GetUserByIdUseCase getUserByIdUseCase,
                          ListUsersUseCase listUsersUseCase, UpdateUserUseCase updateUserUseCase,
                          DeleteUserUseCase deleteUserUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.listUsersUseCase = listUsersUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
    }

    @PostMapping
    @Operation(summary = "Cria um novo usuário", description = "Cria um novo usuário com os dados fornecidos.")
    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "409", description = "Usuário com este e-mail já existe")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        UserResponse response = createUserUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Lista todos os usuários", description = "Retorna uma lista de todos os usuários cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
    public ResponseEntity<List<UserResponse>> listUsers() {
        List<UserResponse> users = listUsersUseCase.execute();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtém um usuário pelo ID", description = "Retorna os detalhes de um usuário específico.")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(getUserByIdUseCase.execute(new UserId(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um usuário", description = "Atualiza os dados de um usuário existente.")
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody @Valid UpdateUserRequest request) {
        UserResponse updatedUser = updateUserUseCase.execute(new UserId(id), request);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um usuário", description = "Exclui um usuário pelo ID.")
    @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        deleteUserUseCase.execute(new UserId(id));
        return ResponseEntity.noContent().build();
    }
}