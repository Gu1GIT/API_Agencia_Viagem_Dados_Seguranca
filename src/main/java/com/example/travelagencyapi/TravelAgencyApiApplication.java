// src/main/java/com/example/travelagencyapi/TravelAgencyApiApplication.java
package com.example.travelagencyapi;

import com.example.travelagencyapi.model.security.Role;
import com.example.travelagencyapi.model.security.User;
import com.example.travelagencyapi.repository.security.RoleRepository;
import com.example.travelagencyapi.repository.security.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

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

    /**
     * Bean que executa código assim que a aplicação é iniciada.
     * Usado aqui para pré-carregar usuários e papéis no banco de dados para fins de teste.
     *
     * @param userRepository Repositório para gerenciar usuários.
     * @param roleRepository Repositório para gerenciar papéis.
     * @param passwordEncoder Codificador de senhas para armazenar senhas de forma segura.
     * @return Uma instância de CommandLineRunner.
     */
    @Bean
    public CommandLineRunner demoData(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Criação de papéis se não existirem
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));

            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));

            // Criação de usuário ADMIN se não existir
            Optional<User> adminUser = userRepository.findByUsername("admin");
            if (adminUser.isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("adminpass")); // Codifica a senha
                admin.setRoles(new HashSet<>(Collections.singletonList(adminRole)));
                userRepository.save(admin);
                System.out.println("Usuário 'admin' criado com sucesso!");
            } else {
                System.out.println("Usuário 'admin' já existe.");
            }

            // Criação de usuário USER se não existir
            Optional<User> normalUser = userRepository.findByUsername("user");
            if (normalUser.isEmpty()) {
                User user = new User();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("userpass")); // Codifica a senha
                user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
                userRepository.save(user);
                System.out.println("Usuário 'user' criado com sucesso!");
            } else {
                System.out.println("Usuário 'user' já existe.");
            }
        };
    }
}
