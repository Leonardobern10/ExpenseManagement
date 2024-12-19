package com.example.ExpenseManagement.config;

import com.example.ExpenseManagement.model.User;
import com.example.ExpenseManagement.repository.UserRepository;
import com.example.ExpenseManagement.util.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtRequestFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Recuperar o cabeçalho Authorization
        String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // Verificar se o cabeçalho contém um token
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7); // Remover o prefixo "Bearer "
            username = jwtUtil.extractUsername(jwt); // Extrair o nome de usuário do token
        }

        // Se o nome de usuário for encontrado e não estiver autenticado
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<User> user = userRepository.findByUsername(username);

            // Verificar se o usuário existe
            if (user.isPresent()) {
                // Verificar se o token é válido
                if (jwtUtil.extractUsername(jwt).equals(username)) {
                    // Criar um objeto de autenticação
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    user.get(),
                                    null,
                                    user.get().getAuthorities() // Agora estamos chamando getAuthorities() corretamente
                            );
                    // Associar o request com os detalhes da autenticação
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Configurar o contexto de segurança
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }

        // Continuar com a cadeia de filtros
        chain.doFilter(request, response);
    }
}
