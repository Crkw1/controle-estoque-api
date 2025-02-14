package br.com.controleestoqueapi.users.application.dto;

import br.com.controleestoqueapi.users.domain.model.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record UpdateUserRequest(
        @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
        String name,

        @Email(message = "Invalid email format")
        String email,

        String phoneNumber,
        String address,
        Set<UserRole> roles
) {}