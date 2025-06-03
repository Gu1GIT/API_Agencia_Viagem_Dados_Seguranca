// src/main/java/com/example/travelagencyapi/config/WebConfig.java
package com.example.travelagencyapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Aplica o CORS a todos os endpoints sob /api/
                .allowedOrigins("*") // Permite requisições de qualquer origem (para desenvolvimento)
                                     // Em produção, você colocaria domínios específicos:
                                     // .allowedOrigins("http://seu-dominio.com", "https://outro-dominio.com")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") // Métodos HTTP permitidos
                .allowedHeaders("*"); // Permite todos os cabeçalhos
    }
}