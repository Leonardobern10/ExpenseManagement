package com.example.ExpenseManagement.service;

import com.example.ExpenseManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserSearchService {

    @Autowired
    private final UserRepository userRepository;

    public UserSearchService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String searchUsername () throws IllegalArgumentException {
        // Obtém o nome de usuário a partir do contexto de segurança
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();  // O nome de usuário está no contexto de autenticação

        // Busca o ID do usuário correspondente
        return String.valueOf(userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("User not found!")
        ).getId());
    }
}
