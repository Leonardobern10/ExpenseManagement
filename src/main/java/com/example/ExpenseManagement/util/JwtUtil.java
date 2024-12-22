package com.example.ExpenseManagement.util;

import java.nio.charset.StandardCharsets;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret; // * Define a chave secreta com base em variaveis de ambiente
    @Value("${jwt.expiration}")
    private long expirationTime; // * Define o tempo de expiração to token

    public String extractUsername(String token) {
        SecretKey key = getSigningKey();
        try {
            return Jwts.parser().verifyWith(key)    // Usando o setSigningKey com o parserBuilder()
                       .build().parseSignedClaims(token).getPayload().getSubject();
        } catch (JwtException e) {
            System.out.println("Invalid JWT: {} " + e.getMessage());

            throw new RuntimeException("Jwt validation failed");
        }
    }

    public String generateToken(String username) {
        SecretKey key = getSigningKey();

        return Jwts.builder()
                   .subject(username)
                   .issuedAt(new Date())
                   .expiration(new Date(
                           System.currentTimeMillis() + expirationTime))
                   .signWith(key)
                   .compact();
    }

    public SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8));
    }
}
