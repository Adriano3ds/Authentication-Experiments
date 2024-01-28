package io.adriano3ds.AuthenticationExperiments.dtos.responses;

import io.adriano3ds.AuthenticationExperiments.dtos.responses.data.ResponseData;

public record Response (
        boolean error,
        String message,
        ResponseData data
) {
    public static Response error(String message, ResponseData data) {
        return new Response(true, message, data);
    }

    public static Response ok(String message, ResponseData data) {
        return new Response(false, message, data);
    }
}
