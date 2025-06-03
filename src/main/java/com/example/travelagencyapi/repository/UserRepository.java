// src/main/java/com/example/travelagencyapi/repository/security/UserRepository.java
package com.example.travelagencyapi.repository.security;

import com.example.travelagencyapi.model.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Interface de repositório para a entidade User.
 * Estende JpaRepository para fornecer operações CRUD básicas e outras funcionalidades de persistência.
 *
 * O Spring Data JPA cria automaticamente uma implementação desta interface em tempo de execução.
 */
@Repository // Indica que esta interface é um componente de repositório gerenciado pelo Spring
public interface UserRepository extends JpaRepository<User, Long> {
    // JpaRepository<Tipo da Entidade, Tipo da Chave Primária>

    /**
     * Encontra um usuário pelo seu nome de usuário.
     * O Spring Data JPA gera a query automaticamente com base no nome do método.
     * @param username O nome de usuário a ser pesquisado.
     * @return Um Optional contendo o usuário se encontrado, ou um Optional vazio caso contrário.
     */
    Optional<User> findByUsername(String username);
}
