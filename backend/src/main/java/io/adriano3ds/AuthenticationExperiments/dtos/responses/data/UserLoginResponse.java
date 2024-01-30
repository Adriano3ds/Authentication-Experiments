package io.adriano3ds.AuthenticationExperiments.dtos.responses.data;

public record UserLoginResponse(
        String token
) implements ResponseData {
}
