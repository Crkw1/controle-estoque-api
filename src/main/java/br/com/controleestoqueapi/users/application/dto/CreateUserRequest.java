
package br.com.controleestoqueapi.users.application.dto;

import br.com.controleestoqueapi.users.domain.model.enums.UserRole;
import java.util.Set;
public record CreateUserRequest(
        String name,
        String email,
        String password,
        String phoneNumber,
        String address,
        Set<UserRole> roles
) {}