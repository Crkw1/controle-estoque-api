package br.com.controleestoqueapi.users.domain.repository;

import br.com.controleestoqueapi.users.domain.model.User;
import br.com.controleestoqueapi.users.domain.model.UserId;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);  // Salva *ou atualiza* (o adaptador decide)
    Optional<User> findById(UserId id); // Usa o Value Object
    List<User> findAll();
    void delete(UserId id);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
