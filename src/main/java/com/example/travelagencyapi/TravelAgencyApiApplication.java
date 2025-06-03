// src/main/java/com/example/travelagencyapi/TravelAgencyApiApplication.java
package com.example.travelagencyapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação Spring Boot para a API da Agência de Viagens.
 * Esta classe é o ponto de entrada para a execução da aplicação.
 */
@SpringBootApplication // Anotação que combina @Configuration, @EnableAutoConfiguration e @ComponentScan
public class TravelAgencyApiApplication {

    /**
     * Método principal que inicia a aplicação Spring Boot.
     * @param args Argumentos da linha de comando.
     */
    public static void main(String[] args) {
        SpringApplication.run(TravelAgencyApiApplication.class, args);
    }

}
