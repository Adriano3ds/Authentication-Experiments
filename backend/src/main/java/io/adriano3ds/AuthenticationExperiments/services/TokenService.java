package io.adriano3ds.AuthenticationExperiments.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.adriano3ds.AuthenticationExperiments.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${api.security.token.issuer}")
    private String issuer;

    @Value("${api.security.token.expiration-in-hours}")
    private int expirationInHours;

    public String generateToken(User user) {
        Algorithm algo = Algorithm.HMAC512(secret);
        try {
            return JWT.create()
                    .withIssuer(issuer)
                    .withSubject(user.getId().toString())
                    .withExpiresAt(getExpirationDate())
                    .sign(algo);
        } catch (JWTCreationException ex) {
            throw new RuntimeException("Error while generating token", ex);
        }
    }

    private Instant getExpirationDate() {
        return LocalDateTime.now().plusHours(expirationInHours).toInstant(ZoneOffset.of("-03:00"));
    }

    public Long getUserIdFromTokenIfValid(String token) {
        Algorithm algo = Algorithm.HMAC512(secret);
        try {
            String subjectString = JWT.require(algo)
                    .withIssuer(issuer)
                    .build()
                    .verify(token)
                    .getSubject();
            return Long.parseLong(subjectString);
        } catch (JWTVerificationException | NumberFormatException ex) {
            return null;
        }
    }
}
