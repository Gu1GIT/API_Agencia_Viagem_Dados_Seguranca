// src/main/java/com/example/travelagencyapi/controller/DestinationController.java
package com.example.travelagencyapi.controller;

import com.example.travelagencyapi.model.Destination;
import com.example.travelagencyapi.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para gerenciar operações relacionadas a destinos de viagem.
 * Define os endpoints da API e lida com as requisições HTTP.
 */
@RestController // Indica que esta classe é um controlador REST
@RequestMapping("/api/destinations") // Define o caminho base para todos os endpoints neste controlador
public class DestinationController {

    private final DestinationService destinationService; // Injeção de dependência do serviço

    /**
     * Construtor para injeção de dependência do DestinationService.
     * @param destinationService O serviço de destinos.
     */
    @Autowired // Marca o construtor para injeção automática de dependências
    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    /**
     * Endpoint para cadastrar um novo destino de viagem.
     * Método HTTP: POST
     * URL: /api/destinations
     * Corpo da requisição: JSON representando um objeto Destination.
     * @param destination O objeto Destination a ser criado, recebido do corpo da requisição.
     * @return ResponseEntity contendo o destino criado e o status HTTP 201 (Created).
     */
    @PostMapping
    public ResponseEntity<Destination> createDestination(@RequestBody Destination destination) {
        Destination createdDestination = destinationService.createDestination(destination);
        return new ResponseEntity<>(createdDestination, HttpStatus.CREATED);
    }

    /**
     * Endpoint para listar todos os destinos de viagem disponíveis.
     * Método HTTP: GET
     * URL: /api/destinations
     * @return ResponseEntity contendo uma lista de destinos e o status HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<Destination>> getAllDestinations() {
        List<Destination> destinations = destinationService.getAllDestinations();
        return new ResponseEntity<>(destinations, HttpStatus.OK);
    }

    /**
     * Endpoint para pesquisar destinos por nome ou localização.
     * Método HTTP: GET
     * URL: /api/destinations/search?query=nome_ou_localizacao
     * @param query O termo de pesquisa, passado como parâmetro de consulta.
     * @return ResponseEntity contendo uma lista de destinos correspondentes e o status HTTP 200 (OK),
     * ou 404 (Not Found) se nenhum destino for encontrado.
     */
    @GetMapping("/search")
    public ResponseEntity<List<Destination>> searchDestinations(@RequestParam String query) {
        List<Destination> destinations = destinationService.searchDestinations(query);
        if (destinations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(destinations, HttpStatus.OK);
    }

    /**
     * Endpoint para visualizar informações detalhadas sobre um destino específico.
     * Método HTTP: GET
     * URL: /api/destinations/{id}
     * @param id O ID do destino, passado como variável de caminho.
     * @return ResponseEntity contendo o destino e o status HTTP 200 (OK) se encontrado,
     * ou 404 (Not Found) caso contrário.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Destination> getDestinationById(@PathVariable Long id) {
        Optional<Destination> destination = destinationService.getDestinationById(id);
        return destination.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint para avaliar um destino de viagem.
     * Método HTTP: PATCH (usado para atualização parcial)
     * URL: /api/destinations/{id}/rate?rating=nota
     * @param id O ID do destino a ser avaliado.
     * @param rating A nota de avaliação (1-10), passada como parâmetro de consulta.
     * @return ResponseEntity contendo o destino atualizado e o status HTTP 200 (OK) se a avaliação for bem-sucedida,
     * ou 400 (Bad Request) se a avaliação for inválida, ou 404 (Not Found) se o destino não existir.
     */
    @PatchMapping("/{id}/rate")
    public ResponseEntity<Destination> rateDestination(@PathVariable Long id, @RequestParam int rating) {
        Optional<Destination> updatedDestination = destinationService.evaluateDestination(id, rating);
        return updatedDestination.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST)); // Ou NOT_FOUND se o ID for inválido
    }

    /**
     * Endpoint para excluir um determinado destino de viagem.
     * Método HTTP: DELETE
     * URL: /api/destinations/{id}
     * @param id O ID do destino a ser excluído.
     * @return ResponseEntity com status HTTP 204 (No Content) se a exclusão for bem-sucedida,
     * ou 404 (Not Found) caso contrário.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDestination(@PathVariable Long id) {
        boolean deleted = destinationService.deleteDestination(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Endpoint para atualizar completamente um destino existente.
     * Método HTTP: PUT
     * URL: /api/destinations/{id}
     * Corpo da requisição: JSON representando o objeto Destination completo com as novas informações.
     * @param id O ID do destino a ser atualizado.
     * @param destination O objeto Destination com as informações atualizadas.
     * @return ResponseEntity contendo o destino atualizado e o status HTTP 200 (OK) se a atualização for bem-sucedida,
     * ou 404 (Not Found) se o destino não existir.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Destination> updateDestination(@PathVariable Long id, @RequestBody Destination destination) {
        Optional<Destination> updated = destinationService.updateDestination(id, destination);
        return updated.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
