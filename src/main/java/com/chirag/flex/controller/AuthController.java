package com.chirag.flex.controller;

import com.chirag.flex.dto.LoginRequest;
import com.chirag.flex.dto.ResetRequest;
import com.chirag.flex.dto.SignupRequest;
import com.chirag.flex.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
	

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest req) {

        try {
            String result = authService.signup(req.getEmail(), req.getPassword());
            return ResponseEntity.ok(result);

        } catch (ResponseStatusException e) {
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body(e.getReason());
        }
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest req){
        return authService.login(req.getEmail(), req.getPassword());
    }

    @PostMapping("/forgot")
    public ResponseEntity<?> forgot(@RequestBody SignupRequest req) {
        authService.forgotPassword(req.getEmail());
        return ResponseEntity.ok("Reset link sent");
    }
    @PostMapping("/reset")
    public String resetPassword(@RequestBody ResetRequest req) {
        authService.resetPassword(req.getToken(), req.getNewPassword());
        return "Password updated successfully";
    }
}