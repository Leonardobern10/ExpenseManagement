package com.example.ExpenseManagement.controller;

import com.example.ExpenseManagement.dto.AuthRequest;
import com.example.ExpenseManagement.dto.AuthResponse;
import com.example.ExpenseManagement.model.user.User;
import com.example.ExpenseManagement.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST responsável pela autenticação de usuários.
 * Oferece endpoints para registrar um novo usuário e para realizar login.
 */
@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final AuthService authService;

    /**
     * Construtor da classe {@code UserController}.
     *
     * @param authService Serviço responsável pelas operações de autenticação e registro de usuários.
     */
    public UserController (AuthService authService) {
        this.authService = authService;
    }


    /**
     * Cria um novo usuário no sistema.
     *
     * @param authRequest Contém os dados do usuário a ser registrado (nome, email, senha, etc.).
     * @return O usuário recém-criado, com seus dados.
     */
    @PostMapping("/register")
    public ResponseEntity<User> createUser (
            @RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.register(authRequest));
    }

    /**
     * Realiza o login de um usuário no sistema.
     *
     * @param authRequest Contém as credenciais de login (email e senha).
     * @return Uma resposta com um token de autenticação para o usuário autenticado.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser (
            @RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.login(authRequest));
    }

}
