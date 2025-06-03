// src/main/java/com/example/travelagencyapi/service/security/UserDetailsServiceImpl.java
package com.example.travelagencyapi.service.security;

import com.example.travelagencyapi.model.security.User;
import com.example.travelagencyapi.repository.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementação do UserDetailsService do Spring Security.
 * Responsável por carregar os detalhes do usuário do banco de dados para o processo de autenticação.
 */
@Service // Indica que esta classe é um componente de serviço gerenciado pelo Spring.
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository; // Injeção de dependência do repositório de usuários

    @Autowired // Injeta o UserRepository via construtor
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Carrega os detalhes do usuário pelo nome de usuário.
     * Este método é chamado pelo Spring Security durante o processo de autenticação.
     * @param username O nome de usuário para carregar.
     * @return Um objeto UserDetails contendo os detalhes do usuário.
     * @throws UsernameNotFoundException Se o usuário não for encontrado.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca o usuário no banco de dados usando o UserRepository
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
        
        // Retorna o objeto User (que implementa UserDetails)
        return user;
    }
}
