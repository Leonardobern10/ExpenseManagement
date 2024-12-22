package com.example.ExpenseManagement.config;

import com.example.ExpenseManagement.model.User;
import com.example.ExpenseManagement.repository.UserRepository;
import com.example.ExpenseManagement.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Filtro que intercepta todas as solicitações HTTP para validar tokens JWT.
 *
 * <p>Esse filtro verifica o cabeçalho Authorization de cada solicitação, extrai o token JWT, valida-o,
 * e autentica o usuário associado, se o token for válido.</p>
 *
 * @author Leonardo Bernardo
 * @version 1.0
 * @since 2024-12-21
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    /**
     * Construtor da classe JwtRequestFilter.
     *
     * @param jwtUtil        Utilitário para manipulação de tokens JWT.
     * @param userRepository Repositório para busca de informações de usuários.
     */
    public JwtRequestFilter (JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    /**
     * Método que intercepta cada requisição para validar o token JWT e autenticar o usuário.
     *
     * @param request  Objeto que representa a solicitação HTTP.
     * @param response Objeto que representa a resposta HTTP.
     * @param chain    Cadeia de filtros para continuar o processamento.
     * @throws ServletException Se ocorrer algum erro durante o processamento do filtro.
     * @throws IOException      Se ocorrer algum erro de entrada/saída.
     */
    @Override
    protected void doFilterInternal (HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain chain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");
        String jwt = null;
        String username = null;

        if ( authorizationHeader != null && authorizationHeader.startsWith("Bearer ") ) {
            jwt = authorizationHeader.substring(7); // Remover o prefixo "Bearer "
            username = jwtUtil.extractUsername(jwt); // Extrair o nome de usuário do token
        }

        // Se o nome de usuário for encontrado e não estiver autenticado
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null ) {
            Optional<User> user = userRepository.findByUsername(username);

            // Verificar se o usuário existe
            if ( user.isPresent() ) {
                // Verificar se o token é válido
                if ( jwtUtil.extractUsername(jwt).equals(username) ) {
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
