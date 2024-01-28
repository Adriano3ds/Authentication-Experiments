package io.adriano3ds.AuthenticationExperiments.controllers;

import io.adriano3ds.AuthenticationExperiments.dtos.requests.UserRegistrationRequest;
import io.adriano3ds.AuthenticationExperiments.dtos.responses.Response;
import io.adriano3ds.AuthenticationExperiments.dtos.responses.data.ResponseData;
import io.adriano3ds.AuthenticationExperiments.dtos.responses.data.UserRegistrationResponse;
import io.adriano3ds.AuthenticationExperiments.exceptions.EmailAlreadyExistsException;
import io.adriano3ds.AuthenticationExperiments.models.User;
import io.adriano3ds.AuthenticationExperiments.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody @Valid UserRegistrationRequest data) {
        User user = new User();
        BeanUtils.copyProperties(data, user);
        User savedUser;
        try {
            savedUser = userService.registerUser(user);
        } catch (EmailAlreadyExistsException ex) {
            Response response = Response.error(ex.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
        ResponseData responseData = new UserRegistrationResponse(savedUser.getId(),
                savedUser.getName(), savedUser.getEmail());
        Response response = Response.ok("User registered successfully", responseData);
        return ResponseEntity.ok().body(response);
    }

}
