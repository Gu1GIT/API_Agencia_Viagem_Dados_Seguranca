// src/main/java/com/example/travelagencyapi/config/SecurityConfig.java
package com.example.travelagencyapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher; // Importe para usar AntPathRequestMatcher

/**
 * Classe de configuração principal para o Spring Security.
 * Define a cadeia de filtros de segurança, o gerenciamento de senhas e a autenticação.
 */
@Configuration // Indica que esta classe é uma fonte de definições de beans para o contêiner Spring.
@EnableWebSecurity // Habilita a segurança web do Spring Security.
@EnableMethodSecurity // Habilita a segurança baseada em anotações de método (ex: @PreAuthorize).
public class SecurityConfig {

    /**
     * Define o codificador de senhas a ser utilizado.
     * BCryptPasswordEncoder é recomendado para armazenar senhas de forma segura.
     * @return Uma instância de PasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura o provedor de autenticação que usará o UserDetailsService e o PasswordEncoder.
     * @param userDetailsService O serviço para carregar detalhes do usuário.
     * @param passwordEncoder O codificador de senhas.
     * @return Uma instância de AuthenticationProvider.
     */
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService); // Define o serviço que carregará os usuários
        authenticationProvider.setPasswordEncoder(passwordEncoder); // Define o codificador de senhas
        return authenticationProvider;
    }

    /**
     * Expõe o AuthenticationManager como um bean.
     * @param config A configuração de autenticação.
     * @return Uma instância de AuthenticationManager.
     * @throws Exception Se ocorrer um erro.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Define a cadeia de filtros de segurança HTTP.
     * Configura as regras de autorização para diferentes URLs e desabilita o CSRF para APIs REST.
     * @param http O objeto HttpSecurity para configurar a segurança.
     * @return Uma instância de SecurityFilterChain.
     * @throws Exception Se ocorrer um erro durante a configuração.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // Desabilita CSRF para APIs RESTful (geralmente não é necessário)
            .authorizeHttpRequests(authorize -> authorize
                // Permite acesso público apenas ao endpoint de LOGIN
                .requestMatchers(AntPathRequestMatcher.antMatcher("/api/auth/login")).permitAll()
                // Restringe o endpoint de REGISTRO apenas para usuários com papel ADMIN
                .requestMatchers(AntPathRequestMatcher.antMatcher("/api/auth/register")).hasRole("ADMIN")
                // Permite acesso público a URLs designadas como públicas para destinos (se houver)
                .requestMatchers(AntPathRequestMatcher.antMatcher("/api/destinations/public/**")).permitAll()
                // Requer autenticação para todos os endpoints de destinos
                .requestMatchers(AntPathRequestMatcher.antMatcher("/api/destinations/**")).authenticated()
                // Todas as outras requisições requerem autenticação
                .anyRequest().authenticated()
            )
            .httpBasic(); // Habilita autenticação HTTP Basic (para testes rápidos com Postman/cURL)
            // .formLogin(); // Opcional: para formulários de login baseados em navegador

        return http.build();
    }
}
