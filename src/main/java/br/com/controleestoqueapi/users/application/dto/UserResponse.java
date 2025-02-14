package br.com.controleestoqueapi.users.application.dto;

import br.com.controleestoqueapi.users.domain.model.User;
import br.com.controleestoqueapi.users.domain.model.enums.UserRole;
import java.util.Set;

public record UserResponse(
                            Long id,
                            String name,
                            String email,
                            String phoneNumber,
                            String address,
                            Set<UserRole> roles
) {
    // Método estático para converter da entidade User para o DTO UserResponse
    public static UserResponse fromDomain(User user) {
        return new UserResponse(
                user.getId().value(), // Extrai o valor do UserId
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAddress(),
                user.getRoles()
        );
    }
}