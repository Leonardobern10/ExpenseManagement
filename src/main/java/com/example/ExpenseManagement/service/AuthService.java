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

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserBuilderImpl userBuilder;

    public AuthService (UserRepository userRepository,
                        PasswordEncoder passwordEncoder,
                        JwtUtil jwtUtil, UserBuilderImpl userBuilder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.userBuilder = userBuilder;
    }

    public User register (AuthRequest request) {
        Optional<User> existingUser = userRepository.findByUsername(request.username());
        UserAlwaysExists.validate(existingUser);
        User user = UserDirector.construct(userBuilder, request.username(),
                passwordEncoder.encode(request.password()), Role.USER, request.email());

        return userRepository.save(user);
    }

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

    private void checkCredentials (AuthRequest request, String userPassword) throws RuntimeException {
        if (! passwordEncoder.matches(request.password(), userPassword)) {
            throw new IllegalArgumentException("Invalid username or password!");
        }
    }

}
