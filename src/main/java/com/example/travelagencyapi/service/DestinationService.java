// src/main/java/com/example/travelagencyapi/service/DestinationService.java
package com.example.travelagencyapi.service;

import com.example.travelagencyapi.model.Destination;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Camada de serviço para gerenciar operações de negócios relacionadas a destinos.
 * Utiliza uma lista em memória para simular o armazenamento de dados,
 * pois a integração com banco de dados não é um requisito para este desafio.
 */
@Service
public class DestinationService {

    // Lista em memória para armazenar destinos
    private final List<Destination> destinations = new ArrayList<>();
    // Contador atômico para gerar IDs únicos para novos destinos
    private final AtomicLong counter = new AtomicLong();

    /**
     * Cria um novo destino e o adiciona à lista.
     * @param destination O objeto Destination a ser criado.
     * @return O destino criado com um ID gerado.
     */
    public Destination createDestination(Destination destination) {
        destination.setId(counter.incrementAndGet()); // Atribui um novo ID
        destinations.add(destination); // Adiciona à lista em memória
        return destination;
    }

    /**
     * Retorna uma lista de todos os destinos disponíveis.
     * @return Uma nova lista contendo todos os destinos.
     */
    public List<Destination> getAllDestinations() {
        return new ArrayList<>(destinations); // Retorna uma cópia para evitar modificações externas
    }

    /**
     * Pesquisa destinos por nome ou localização.
     * A pesquisa não diferencia maiúsculas de minúsculas.
     * @param query A string de pesquisa (nome ou localização).
     * @return Uma lista de destinos que correspondem à consulta.
     */
    public List<Destination> searchDestinations(String query) {
        String lowerCaseQuery = query.toLowerCase();
        return destinations.stream()
                .filter(d -> d.getName().toLowerCase().contains(lowerCaseQuery) ||
                             d.getLocation().toLowerCase().contains(lowerCaseQuery))
                .collect(Collectors.toList());
    }

    /**
     * Retorna um destino específico pelo seu ID.
     * @param id O ID do destino a ser recuperado.
     * @return Um Optional contendo o destino se encontrado, ou um Optional vazio caso contrário.
     */
    public Optional<Destination> getDestinationById(Long id) {
        return destinations.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst();
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
        // Valida se a avaliação está dentro do intervalo permitido
        if (rating < 1 || rating > 10) {
            System.err.println("Avaliação inválida: " + rating + ". Deve estar entre 1 e 10.");
            return Optional.empty(); // Retorna vazio para avaliação inválida
        }

        Optional<Destination> optionalDestination = getDestinationById(id);
        if (optionalDestination.isPresent()) {
            Destination destination = optionalDestination.get();
            // Calcula a soma total das avaliações existentes
            double currentTotalRating = destination.getAverageRating() * destination.getNumberOfRatings();
            // Incrementa o contador de avaliações
            int newNumberOfRatings = destination.getNumberOfRatings() + 1;
            // Calcula a nova avaliação média
            double newAverageRating = (currentTotalRating + rating) / newNumberOfRatings;

            destination.setAverageRating(newAverageRating);
            destination.setNumberOfRatings(newNumberOfRatings);
            System.out.println("Destino " + destination.getName() + " avaliado com " + rating + ". Nova média: " + newAverageRating);
            return Optional.of(destination);
        }
        System.err.println("Destino com ID " + id + " não encontrado para avaliação.");
        return Optional.empty(); // Destino não encontrado
    }

    /**
     * Exclui um destino da lista pelo seu ID.
     * @param id O ID do destino a ser excluído.
     * @return true se o destino foi excluído com sucesso, false caso contrário.
     */
    public boolean deleteDestination(Long id) {
        boolean removed = destinations.removeIf(d -> d.getId().equals(id));
        if (removed) {
            System.out.println("Destino com ID " + id + " excluído com sucesso.");
        } else {
            System.err.println("Destino com ID " + id + " não encontrado para exclusão.");
        }
        return removed;
    }

    /**
     * Atualiza as informações de um destino existente.
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
            // A avaliação e o número de avaliações são atualizados apenas via evaluateDestination
            System.out.println("Destino com ID " + id + " atualizado com sucesso.");
            return Optional.of(existingDestination);
        }
        System.err.println("Destino com ID " + id + " não encontrado para atualização.");
        return Optional.empty();
    }
}
