package com.example.ExpenseManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Classe de configuração de segurança para a aplicação.
 *
 * <p>Define a configuração de segurança usando o Spring Security, incluindo filtros, codificação de senha e
 * gerenciamento de autenticação.</p>
 *
 * @author Leonardo Bernardo
 * @version 1.0
 * @since 2024-12-21
 */
@Configuration
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;


    /**
     * Construtor da classe SecurityConfig.
     *
     * @param jwtRequestFilter Filtro para validar tokens JWT em requisições HTTP.
     */
    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    /**
     * Configura a cadeia de filtros de segurança para a aplicação.
     *
     * <p>Desativa CSRF, define as permissões para diferentes endpoints, e adiciona o filtro JWT antes do filtro de autenticação padrão.</p>
     *
     * @param http Objeto HttpSecurity para configuração de segurança da aplicação.
     * @return Uma instância de {@link SecurityFilterChain} configurada.
     * @throws Exception Se ocorrer algum erro durante a configuração.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                // Adicionar o filtro antes do filtro de autenticação padrão
                .addFilterBefore(jwtRequestFilter,
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
