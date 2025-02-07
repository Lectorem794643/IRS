package ru.fns.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fns.server.dto.AuthRequest;
import ru.fns.server.dto.AuthResponse;
import ru.fns.server.service.AuthService;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(
            summary = "Авторизация пользователя",
            description = "Процесс авторизации пользователя с использованием логина и пароля"
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        log.info("Attempting to authenticate user: {}", authRequest.getLogin());

        AuthResponse response = authService.authorize(authRequest.getLogin(), authRequest.getPassword());

        return ResponseEntity.ok(response);
    }
}


