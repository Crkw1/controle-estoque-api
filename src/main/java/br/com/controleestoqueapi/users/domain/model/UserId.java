package br.com.controleestoqueapi.users.domain.model;

import java.util.Objects;

public record UserId(Long value) {
    public UserId {
        Objects.requireNonNull(value, "User ID cannot be null");
        if (value <= 0) {
            throw new IllegalArgumentException("User ID must be positive");
        }
    }
}
