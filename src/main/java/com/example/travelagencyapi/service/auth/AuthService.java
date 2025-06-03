// src/main/java/com/example/travelagencyapi/service/auth/AuthService.java
package com.example.travelagencyapi.service.auth;

import com.example.travelagencyapi.model.security.Role;
import com.example.travelagencyapi.model.security.User;
import com.example.travelagencyapi.repository.security.RoleRepository;
import com.example.travelagencyapi.repository.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

/**
 * Serviço responsável pela lógica de autenticação e registro de usuários.
 */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager; // Usado para autenticar o usuário após o registro

    @Autowired
    public AuthService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Registra um novo usuário no sistema.
     * @param username O nome de usuário.
     * @param password A senha (será codificada).
     * @param roleName O nome do papel (ex: "ROLE_USER", "ROLE_ADMIN").
     * @return O usuário registrado.
     * @throws RuntimeException Se o nome de usuário já existir ou o papel não for encontrado.
     */
    public User registerUser(String username, String password, String roleName) {
        // Verifica se o usuário já existe
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Nome de usuário já existe!");
        }

        // Busca ou cria o papel
        Role role = roleRepository.findByName(roleName)
                .orElseGet(() -> {
                    Role newRole = new Role(roleName);
                    return roleRepository.save(newRole);
                });

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // Codifica a senha
        user.setRoles(new HashSet<>(Collections.singletonList(role))); // Atribui o papel

        return userRepository.save(user);
    }

    /**
     * Autentica um usuário.
     * @param username O nome de usuário.
     * @param password A senha.
     * @return O objeto Authentication se a autenticação for bem-sucedida.
     * @throws RuntimeException Se a autenticação falhar.
     */
    public Authentication authenticateUser(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return authentication;
        } catch (Exception e) {
            throw new RuntimeException("Falha na autenticação: " + e.getMessage());
        }
    }
}
