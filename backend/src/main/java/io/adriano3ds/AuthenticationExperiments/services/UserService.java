package io.adriano3ds.AuthenticationExperiments.services;

import io.adriano3ds.AuthenticationExperiments.exceptions.EmailAlreadyExistsException;
import io.adriano3ds.AuthenticationExperiments.models.User;
import io.adriano3ds.AuthenticationExperiments.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @Transactional
    public User registerUser(User user) throws EmailAlreadyExistsException {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new EmailAlreadyExistsException(user.getEmail());
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encryptedPassword);
        return userRepository.save(user);
    }

    public String authenticateUser(User user) throws AuthenticationException {
        UsernamePasswordAuthenticationToken usernamePassword =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication auth = authenticationManager.authenticate(usernamePassword);
        SecurityContextHolder.getContext().setAuthentication(auth);

        return tokenService.generateToken((User) auth.getPrincipal());
    }
}
