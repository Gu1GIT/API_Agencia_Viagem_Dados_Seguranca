// src/main/java/com/example/travelagencyapi/model/security/User.java
package com.example.travelagencyapi.model.security;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Entidade que representa um usuário do sistema, mapeada para uma tabela no banco de dados.
 * Implementa UserDetails do Spring Security para integração com o sistema de autenticação.
 */
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER) // Carrega os papéis imediatamente com o usuário
    @JoinTable(
        name = "user_roles", // Nome da tabela de junção
        joinColumns = @JoinColumn(name = "user_id"), // Coluna que referencia o usuário
        inverseJoinColumns = @JoinColumn(name = "role_id") // Coluna que referencia o papel
    )
    private Set<Role> roles = new HashSet<>();

    // Construtor padrão necessário para JPA
    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // --- Getters e Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    // --- Métodos de UserDetails (Implementação do Spring Security) ---

    /**
     * Retorna as autoridades (papéis/permissões) concedidas ao usuário.
     * @return Uma coleção de GrantedAuthority.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    /**
     * Indica se a conta do usuário expirou.
     * @return true se a conta é válida, false caso contrário.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true; // Para este exemplo, não implementaremos expiração de conta
    }

    /**
     * Indica se a conta do usuário está bloqueada.
     * @return true se a conta não está bloqueada, false caso contrário.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true; // Para este exemplo, não implementaremos bloqueio de conta
    }

    /**
     * Indica se as credenciais (senha) do usuário expiraram.
     * @return true se as credenciais são válidas, false caso contrário.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Para este exemplo, não implementaremos expiração de credenciais
    }

    /**
     * Indica se o usuário está ativado ou desativado.
     * @return true se o usuário está ativado, false caso contrário.
     */
    @Override
    public boolean isEnabled() {
        return true; // Para este exemplo, o usuário está sempre ativado
    }
}
