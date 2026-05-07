package com.chirag.flex.service;


import com.chirag.flex.entity.User;
import com.chirag.flex.repository.UserRepository;
import com.chirag.flex.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired private UserRepository repo;
    @Autowired private JwtUtil jwt;
    @Autowired private EmailService emailService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String signup(String email,String password){
        if(repo.findByEmail(email).isPresent())
            throw new RuntimeException("Email exists");

        User u = new User();
        u.setEmail(email);
        u.setPassword(encoder.encode(password));
        u.setRole("USER");

        repo.save(u);
        return "Registered";
    }

    public String login(String email,String password){
        User u = repo.findByEmail(email).orElseThrow();

        if(!encoder.matches(password,u.getPassword()))
            throw new RuntimeException("Invalid password");

        return jwt.generateToken(email,u.getRole());
    }

    public void forgotPassword(String email){
        User u = repo.findByEmail(email).orElseThrow();

        String token = UUID.randomUUID().toString();

        u.setResetToken(token);
        u.setTokenExpiry(LocalDateTime.now().plusMinutes(15));

        repo.save(u);

        // ✅ ONLY ONE CLEAN LINK
        String link = "http://192.168.1.10:5500/HTML/abc/reset.html?token=" + token;

        emailService.sendResetEmail(email, link);
    }
    public void resetPassword(String token, String newPassword) {

        User user = repo.findByResetToken(token);

        if (user == null) {
            throw new RuntimeException("Invalid token");
        }

        if (user.getTokenExpiry() == null || user.getTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        user.setPassword(encoder.encode(newPassword));
        user.setResetToken(null);
        user.setTokenExpiry(null);

        repo.save(user);
    } 
}