// src/main/java/com/example/travelagencyapi/service/DestinationService.java
package com.example.travelagencyapi.service;

import com.example.travelagencyapi.model.Destination;
import com.example.travelagencyapi.repository.DestinationRepository; // Importe o novo repositório
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Camada de serviço para gerenciar operações de negócios relacionadas a destinos.
 * Agora utiliza DestinationRepository para persistência de dados no banco de dados.
 */
@Service
public class DestinationService {

    private final DestinationRepository destinationRepository; // Injeção de dependência do repositório

    @Autowired // Injeta o DestinationRepository via construtor
    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    /**
     * Cria um novo destino e o salva no banco de dados.
     * @param destination O objeto Destination a ser criado.
     * @return O destino salvo com o ID gerado pelo banco de dados.
     */
    public Destination createDestination(Destination destination) {
        // O ID será gerado automaticamente pelo banco de dados (GenerationType.IDENTITY)
        return destinationRepository.save(destination);
    }

    /**
     * Retorna uma lista de todos os destinos disponíveis do banco de dados.
     * @return Uma lista contendo todos os destinos.
     */
    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    /**
     * Pesquisa destinos por nome ou localização no banco de dados.
     * A pesquisa não diferencia maiúsculas de minúsculas.
     * @param query A string de pesquisa (nome ou localização).
     * @return Uma lista de destinos que correspondem à consulta.
     */
    public List<Destination> searchDestinations(String query) {
        // Usa o método customizado do repositório para pesquisa
        return destinationRepository.findByNameContainingIgnoreCaseOrLocationContainingIgnoreCase(query, query);
    }

    /**
     * Retorna um destino específico pelo seu ID do banco de dados.
     * @param id O ID do destino a ser recuperado.
     * @return Um Optional contendo o destino se encontrado, ou um Optional vazio caso contrário.
     */
    public Optional<Destination> getDestinationById(Long id) {
        return destinationRepository.findById(id);
    }

    /**
     * Avalia um destino específico e recalcula sua avaliação média.
     * A avaliação deve estar entre 1 e 10.
     * @param id O ID do destino a ser avaliado.
     * @param rating A nota de avaliação (1-10).
     * @return Um Optional contendo o destino atualizado se a avaliação for bem-sucedida,
     * ou um Optional vazio se o ID for inválido ou a avaliação estiver fora do intervalo.
     */
    public Optional<Destination> evaluateDestination(Long id, int rating) {
        if (rating < 1 || rating > 10) {
            System.err.println("Avaliação inválida: " + rating + ". Deve estar entre 1 e 10.");
            return Optional.empty();
        }

        Optional<Destination> optionalDestination = getDestinationById(id);
        if (optionalDestination.isPresent()) {
            Destination destination = optionalDestination.get();
            double currentTotalRating = destination.getAverageRating() * destination.getNumberOfRatings();
            int newNumberOfRatings = destination.getNumberOfRatings() + 1;
            double newAverageRating = (currentTotalRating + rating) / newNumberOfRatings;

            destination.setAverageRating(newAverageRating);
            destination.setNumberOfRatings(newNumberOfRatings);
            
            // Salva as alterações no banco de dados
            return Optional.of(destinationRepository.save(destination));
        }
        System.err.println("Destino com ID " + id + " não encontrado para avaliação.");
        return Optional.empty();
    }

    /**
     * Exclui um destino do banco de dados pelo seu ID.
     * @param id O ID do destino a ser excluído.
     * @return true se o destino foi excluído com sucesso, false caso contrário.
     */
    public boolean deleteDestination(Long id) {
        if (destinationRepository.existsById(id)) {
            destinationRepository.deleteById(id);
            System.out.println("Destino com ID " + id + " excluído com sucesso.");
            return true;
        }
        System.err.println("Destino com ID " + id + " não encontrado para exclusão.");
        return false;
    }

    /**
     * Atualiza as informações de um destino existente no banco de dados.
     * @param id O ID do destino a ser atualizado.
     * @param updatedDestination O objeto Destination com as informações atualizadas.
     * @return Um Optional contendo o destino atualizado se encontrado, ou um Optional vazio caso contrário.
     */
    public Optional<Destination> updateDestination(Long id, Destination updatedDestination) {
        Optional<Destination> existingDestinationOpt = getDestinationById(id);
        if (existingDestinationOpt.isPresent()) {
            Destination existingDestination = existingDestinationOpt.get();
            // Atualiza apenas os campos permitidos para alteração
            existingDestination.setName(updatedDestination.getName());
            existingDestination.setLocation(updatedDestination.getLocation());
            existingDestination.setDescription(updatedDestination.getDescription());
            // Salva as alterações no banco de dados
            return Optional.of(destinationRepository.save(existingDestination));
        }
        System.err.println("Destino com ID " + id + " não encontrado para atualização.");
        return Optional.empty();
    }
}
