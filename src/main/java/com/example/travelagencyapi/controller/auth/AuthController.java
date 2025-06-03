// src/main/java/com/example/travelagencyapi/controller/auth/AuthController.java
package com.example.travelagencyapi.controller.auth;

import com.example.travelagencyapi.dto.LoginRequest;
import com.example.travelagencyapi.dto.RegisterRequest;
import com.example.travelagencyapi.model.security.User;
import com.example.travelagencyapi.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para lidar com operações de autenticação de usuário (registro e login).
 */
@RestController
@RequestMapping("/api/auth") // Endpoint base para autenticação
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager; // Necessário para injetar no AuthService

    @Autowired
    public AuthController(AuthService authService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager; // Injeta AuthenticationManager
    }

    /**
     * Endpoint para registrar um novo usuário.
     * @param registerRequest DTO contendo username, password e role.
     * @return ResponseEntity com o usuário registrado e status 201 (Created).
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest registerRequest) {
        try {
            authService.registerUser(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getRole());
            return new ResponseEntity<>("Usuário registrado com sucesso!", HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint para login de usuário.
     * @param loginRequest DTO contendo username e password.
     * @return ResponseEntity com mensagem de sucesso e status 200 (OK).
     */
    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
            // Se a autenticação foi bem-sucedida, você pode gerar um token JWT aqui se estivesse usando JWT.
            // Para HTTP Basic, a autenticação é stateless e não retorna um token de sessão.
            return new ResponseEntity<>("Login realizado com sucesso para o usuário: " + authentication.getName(), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
