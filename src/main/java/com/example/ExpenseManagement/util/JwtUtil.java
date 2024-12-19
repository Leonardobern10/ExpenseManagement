package com.example.ExpenseManagement.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationTime;

    public SecretKey getSigningKey() {// Substitua pela chave secreta adequada
        // Garante que a chave tenha o tamanho adequado (>= 256 bits para HS256)
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username) {
        SecretKey key = getSigningKey();

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        SecretKey key = getSigningKey();

        try {
            return Jwts.parser()
                .verifyWith(key)  // Usando o setSigningKey com o parserBuilder()
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
        } catch (JwtException e) {
            System.out.println("Invalid JWT: {} " + e.getMessage());
            throw new RuntimeException("Jwt validation failed");
        }
        // Usando o parserBuilder() em vez do parser() (não depreciado)
    }
}
