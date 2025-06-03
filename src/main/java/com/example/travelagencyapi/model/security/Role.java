// src/main/java/com/example/travelagencyapi/model/security/Role.java
package com.example.travelagencyapi.model.security;

import jakarta.persistence.*;

/**
 * Entidade que representa um papel (perfil) de usuário, mapeada para uma tabela no banco de dados.
 */
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name; // Ex: "ROLE_ADMIN", "ROLE_USER"

    // Construtor padrão necessário para JPA
    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    // --- Getters e Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
