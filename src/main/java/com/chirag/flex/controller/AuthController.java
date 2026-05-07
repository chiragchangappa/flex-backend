package com.chirag.flex.controller;

import com.chirag.flex.dto.LoginRequest;
import com.chirag.flex.dto.ResetRequest;
import com.chirag.flex.dto.SignupRequest;
import com.chirag.flex.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest req){
        return service.signup(req.getEmail(), req.getPassword());
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest req){
        return service.login(req.getEmail(), req.getPassword());
    }

    @PostMapping("/forgot")
    public ResponseEntity<?> forgot(@RequestParam String email) {
        service.forgotPassword(email);
        return ResponseEntity.ok("Reset link sent");
    }

    @PostMapping("/reset")
    public String resetPassword(@RequestBody ResetRequest req) {
    	service.resetPassword(req.getToken(), req.getNewPassword());
        return "Password updated successfully";
    }
}