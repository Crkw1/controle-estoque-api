package br.com.controleestoqueapi.users.infrastructure.dto;
import br.com.controleestoqueapi.users.domain.model.enums.UserRole;

import java.util.Set;

public record UserResponse(
        Long id, // Usamos Long, não UserId (o DTO é para o mundo externo)
        String name,
        String email,
        String phoneNumber,
        String address,
        Set<UserRole> roles
) {}