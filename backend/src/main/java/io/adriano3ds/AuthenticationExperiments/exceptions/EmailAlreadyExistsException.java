package io.adriano3ds.AuthenticationExperiments.exceptions;

import lombok.Getter;

@Getter
public class EmailAlreadyExistsException extends AppException {
    private final String email;

    public EmailAlreadyExistsException(String email) {
        super("This email address is already in use.");
        this.email = email;
    }
}
