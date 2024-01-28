package io.adriano3ds.AuthenticationExperiments.dtos.responses.data;

public record UserRegistrationResponse(
        Long id,
        String name,
        String email
) implements ResponseData {
}
