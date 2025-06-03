// src/main/java/com/example/travelagencyapi/dto/RegisterRequest.java
package com.example.travelagencyapi.dto;

/**
 * DTO (Data Transfer Object) para dados de requisição de registro de usuário.
 */
public class RegisterRequest {
    private String username;
    private String password;
    private String role; // Para especificar o papel (ex: "USER", "ADMIN")

    // Construtor padrão
    public RegisterRequest() {
    }

    public RegisterRequest(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters e Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
