package io.adriano3ds.AuthenticationExperiments.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UserLoginRequest(
        @NotBlank
        @Email
        String email,

        @NotBlank
        @Length(min = 8)
        String password
) {
}
