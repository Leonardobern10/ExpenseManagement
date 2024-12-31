package com.example.ExpenseManagement.service;

import com.example.ExpenseManagement.dto.AuthRequest;
import com.example.ExpenseManagement.dto.AuthResponse;
import com.example.ExpenseManagement.model.Role;
import com.example.ExpenseManagement.model.user.User;
import com.example.ExpenseManagement.model.user.UserBuilderImpl;
import com.example.ExpenseManagement.model.user.UserDirector;
import com.example.ExpenseManagement.repository.UserRepository;
import com.example.ExpenseManagement.util.JwtUtil;
import com.example.ExpenseManagement.validations.UserAlwaysExists;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Serviço responsável pela autenticação de usuários, incluindo o registro e login.
 * Esse serviço lida com o processo de criação de novos usuários e a geração de tokens JWT para autenticação.
 */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserBuilderImpl userBuilder;

    /**
     * Construtor para injeção de dependências no serviço de autenticação.
     *
     * @param userRepository Repositório de usuários.
     * @param passwordEncoder Codificador de senhas para garantir a segurança no armazenamento das senhas.
     * @param jwtUtil Utilitário para geração de tokens JWT.
     * @param userBuilder Implementação do builder de usuários.
     */
    public AuthService (UserRepository userRepository,
                        PasswordEncoder passwordEncoder,
                        JwtUtil jwtUtil, UserBuilderImpl userBuilder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.userBuilder = userBuilder;
    }

    /**
     * Realiza o registro de um novo usuário.
     * Verifica se o usuário já existe e cria um novo usuário com as credenciais fornecidas.
     *
     * @param request Objeto contendo as credenciais de registro (nome de usuário, senha e e-mail).
     * @return O usuário registrado.
     * @throws IllegalArgumentException Se o usuário já estiver registrado.
     */
    public User register (AuthRequest request) {
        Optional<User> existingUser = userRepository.findByUsername(request.username());
        UserAlwaysExists.validate(existingUser);
        User user = UserDirector.construct(userBuilder, request.username(),
                passwordEncoder.encode(request.password()), Role.USER, request.email());

        return userRepository.save(user);
    }

    /**
     * Realiza o login de um usuário, validando as credenciais e gerando um token JWT.
     *
     * @param request Objeto contendo as credenciais de login (nome de usuário e senha).
     * @return O token JWT gerado para autenticação.
     * @throws RuntimeException Se as credenciais forem inválidas.
     */
    public AuthResponse login (AuthRequest request) {
        try {
            User user = userRepository.findByUsername(request.username()).orElseThrow(
                    () -> new IllegalArgumentException("User isn't registered!"));
            checkCredentials(request, user.getPassword());
            String token = jwtUtil.generateToken(user.getUsername());
            return new AuthResponse(token);
        } catch ( RuntimeException e ) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Verifica se as credenciais fornecidas (nome de usuário e senha) são válidas.
     *
     * @param request O objeto de solicitação de autenticação com nome de usuário e senha.
     * @param userPassword A senha armazenada do usuário.
     * @throws RuntimeException Se as credenciais forem inválidas.
     */
    private void checkCredentials (AuthRequest request, String userPassword) throws RuntimeException {
        if (! passwordEncoder.matches(request.password(), userPassword)) {
            throw new IllegalArgumentException("Invalid username or password!");
        }
    }

}
