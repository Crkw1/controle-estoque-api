package br.com.controleestoqueapi.users.infrastructure.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;
import br.com.controleestoqueapi.users.domain.model.enums.UserRole;

public record UserRequest(
        @NotBlank(message = "Name cannot be blank")
        @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
        String name,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, message = "Password must be at least 8 characters")
        String password,
        String phoneNumber,
        String address,
        Set<UserRole> roles

) {}