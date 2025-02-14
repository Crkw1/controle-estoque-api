package br.com.controleestoqueapi.users.domain.model;

import br.com.controleestoqueapi.users.domain.exception.DomainValidationException;
import br.com.controleestoqueapi.users.domain.model.enums.UserRole;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;


public class User {

    private final UserId id;
    private final String name;
    private final String email;
    private final String password;
    private final String phoneNumber;
    private final String address;
    private final Set<UserRole> roles;

    public User(String name, String email, String password, String phoneNumber, String address, Set<UserRole> roles) {
        this(null, name, email, password, phoneNumber, address, roles); // Chama o construtor completo
    }

    // Construtor *completo* (com ID) - para reconstrução a partir do banco de dados.
    public User(UserId id, String name, String email, String password, String phoneNumber, String address, Set<UserRole> roles) {
        // Validações *de domínio* (regras de negócio) no construtor.  *Não* use anotações de framework aqui!
        if (name == null || name.isBlank()) {
            throw new DomainValidationException("Name cannot be blank");
        }
        if (name.length() < 3 || name.length() > 255) {
            throw new DomainValidationException("Name must be between 3 and 255 characters");
        }
        if (email == null || email.isBlank() || !isValidEmail(email)) {
            throw new DomainValidationException("Invalid email format");
        }
        if (password == null || password.isBlank()) {
            throw new DomainValidationException("Password cannot be blank");
        }
        if (password.length() < 8) {
            throw new DomainValidationException("Password must be at least 8 characters");
        }
        if (roles == null) {
            throw new DomainValidationException("Roles cannot be null.");
        }

        this.id = id; // Pode ser null na criação
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.roles = new HashSet<>(roles != null ? roles : Set.of());
    }

    public UserId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public boolean isAdmin() {
        return roles.contains(UserRole.ADMIN);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}