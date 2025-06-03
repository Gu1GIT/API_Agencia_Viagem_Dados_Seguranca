// src/main/java/com/example/travelagencyapi/model/Destination.java
package com.example.travelagencyapi.model;

import jakarta.persistence.Entity; // Importe jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue; // Importe jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType; // Importe jakarta.persistence.GenerationType
import jakarta.persistence.Id; // Importe jakarta.persistence.Id
import jakarta.persistence.Table; // Importe jakarta.persistence.Table
import java.util.Objects;

/**
 * Representa um destino de viagem com suas propriedades, mapeado para uma tabela no banco de dados.
 */
@Entity // Indica que esta classe é uma entidade JPA e será mapeada para uma tabela
@Table(name = "destinations") // Opcional: define o nome da tabela no BD. Se omitido, usa o nome da classe.
public class Destination {
    @Id // Marca o campo 'id' como a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura a geração automática de ID pelo banco de dados (ex: SERIAL no PostgreSQL)
    private Long id;
    private String name;
    private String location;
    private String description;
    private double averageRating; // Para armazenar a avaliação média calculada
    private int numberOfRatings; // Para controlar o número de avaliações recebidas

    /**
     * Construtor padrão. Necessário para JPA.
     */
    public Destination() {
    }

    /**
     * Construtor com parâmetros para inicializar um destino.
     * @param id O identificador único do destino.
     * @param name O nome do destino.
     * @param location A localização do destino.
     * @param description Uma descrição detalhada do destino.
     */
    public Destination(Long id, String name, String location, String description) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.description = description;
        this.averageRating = 0.0; // Inicializa a avaliação média como 0
        this.numberOfRatings = 0;   // Inicializa o número de avaliações como 0
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(int numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    /**
     * Sobrescreve o método equals para comparar objetos Destination pelo ID.
     * @param o O objeto a ser comparado.
     * @return true se os objetos forem iguais, false caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Destination that = (Destination) o;
        return Objects.equals(id, that.id);
    }

    /**
     * Sobrescreve o método hashCode.
     * @return O valor hash do objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
