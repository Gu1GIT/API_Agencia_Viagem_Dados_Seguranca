// src/main/java/com/example/travelagencyapi/dto/LoginRequest.java
package com.example.travelagencyapi.dto;

/**
 * DTO (Data Transfer Object) para dados de requisição de login de usuário.
 */
public class LoginRequest {
    private String username;
    private String password;

    // Construtor padrão
    public LoginRequest() {
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
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
}
