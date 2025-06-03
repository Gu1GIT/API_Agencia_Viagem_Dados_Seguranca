// src/main/java/com/example/travelagencyapi/repository/security/RoleRepository.java
package com.example.travelagencyapi.repository.security;

import com.example.travelagencyapi.model.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Interface de repositório para a entidade Role.
 * Estende JpaRepository para fornecer operações CRUD básicas e outras funcionalidades de persistência.
 *
 * O Spring Data JPA cria automaticamente uma implementação desta interface em tempo de execução.
 */
@Repository // Indica que esta interface é um componente de repositório gerenciado pelo Spring
public interface RoleRepository extends JpaRepository<Role, Long> {
    // JpaRepository<Tipo da Entidade, Tipo da Chave Primária>

    /**
     * Encontra um papel pelo seu nome.
     * O Spring Data JPA gera a query automaticamente com base no nome do método.
     * @param name O nome do papel a ser pesquisado (ex: "ROLE_ADMIN", "ROLE_USER").
     * @return Um Optional contendo o papel se encontrado, ou um Optional vazio caso contrário.
     */
    Optional<Role> findByName(String name);
}
