package br.com.controleestoqueapi.users.domain.exception;

public class UserInUseException extends RuntimeException {
    public UserInUseException(String message) {
        super(message);
    }
}
