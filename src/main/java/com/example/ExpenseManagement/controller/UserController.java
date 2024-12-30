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

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final AuthService authService;

    public UserController (AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser (
            @RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.register(authRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser (
            @RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.login(authRequest));
    }

}
