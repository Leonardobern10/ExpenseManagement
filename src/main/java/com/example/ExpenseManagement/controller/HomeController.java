package com.example.ExpenseManagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST responsável por gerenciar a rota inicial da aplicação.
 * Este controlador fornece um endpoint para verificar se o serviço está funcionando corretamente.
 */
@RestController
@RequestMapping("/")
public class HomeController {

    /**
     * Endpoint para verificar o status da aplicação.
     * @return Uma mensagem simples indicando que o serviço está funcionando corretamente.
     */
    @GetMapping
    public ResponseEntity<String> isOk() {
        return ResponseEntity.ok("It's ok!");
    }
}
