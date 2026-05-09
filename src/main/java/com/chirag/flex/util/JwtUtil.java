package com.chirag.flex.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET;

    private SecretKey key;

    // ✅ Create key ONCE (important for consistency)
    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // =====================
    // GENERATE TOKEN
    // =====================
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
                .signWith(key) // ✅ FIXED
                .compact();
    }

    // =====================
    // EXTRACT EMAIL
    // =====================
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key) // ✅ FIXED
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // =====================
    // EXTRACT ROLE
    // =====================
    public String extractRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key) // ✅ FIXED
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }
}