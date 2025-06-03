// src/main/java/com/example/travelagencyapi/repository/DestinationRepository.java
package com.example.travelagencyapi.repository;

import com.example.travelagencyapi.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface de repositório para a entidade Destination.
 * Estende JpaRepository para fornecer operações CRUD básicas e outras funcionalidades de persistência.
 *
 * O Spring Data JPA cria automaticamente uma implementação desta interface em tempo de execução.
 */
@Repository // Indica que esta interface é um componente de repositório gerenciado pelo Spring
public interface DestinationRepository extends JpaRepository<Destination, Long> {
    // JpaRepository<Tipo da Entidade, Tipo da Chave Primária>

    /**
     * Método para pesquisar destinos pelo nome ou localização.
     * O Spring Data JPA gera a query automaticamente com base no nome do método.
     * @param name O nome do destino a ser pesquisado.
     * @param location A localização do destino a ser pesquisada.
     * @return Uma lista de destinos que correspondem ao nome ou localização.
     */
    List<Destination> findByNameContainingIgnoreCaseOrLocationContainingIgnoreCase(String name, String location);
}
