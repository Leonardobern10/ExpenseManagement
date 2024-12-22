package com.example.ExpenseManagement.service;

import com.example.ExpenseManagement.dto.AuthRequest;
import com.example.ExpenseManagement.dto.AuthResponse;
import com.example.ExpenseManagement.model.*;
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
                passwordEncoder.encode(request.password()), Role.USER);

        return userRepository.save(user);
    }

    public AuthResponse login (AuthRequest request) {
        try {
            User user = userRepository.findByUsername(request.username()).orElseThrow(
                    () -> new IllegalArgumentException("User isn't registered!"));

            if (!passwordEncoder.matches(request.password(), user.getPassword())) {
                throw new IllegalArgumentException("Invalid username or password!");
            }

            String token = jwtUtil.generateToken(user.getUsername());
            return new AuthResponse(token);
        } catch (RuntimeException e ) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
