package br.com.controleestoqueapi.users.infrastructure.controller;

import br.com.controleestoqueapi.users.domain.model.User;
import br.com.controleestoqueapi.users.domain.service.UserService;
import br.com.controleestoqueapi.users.infrastructure.dto.UserDtoRequest;
import br.com.controleestoqueapi.users.infrastructure.dto.UserDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDtoResponse> createUser(@RequestBody UserDtoRequest userDtoRequest) {
        User user = userDtoRequest.toDomain();
        User userCreated = userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserDtoResponse.fromDomain(userCreated));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDtoResponse> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(UserDtoResponse.fromDomain(user));
    }

    @GetMapping
    public ResponseEntity<List<UserDtoResponse>> listUsers() {
        List<User> users = userService.listUsers();
        return ResponseEntity.ok(users.stream().map(UserDtoResponse::fromDomain).collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDtoResponse> updateUser(@PathVariable Long id, @RequestBody UserDtoRequest userDtoRequest) {
        User user = userDtoRequest.toDomain();
        User userUpdated = userService.updateUser(id, user);
        return ResponseEntity.ok(UserDtoResponse.fromDomain(userUpdated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}