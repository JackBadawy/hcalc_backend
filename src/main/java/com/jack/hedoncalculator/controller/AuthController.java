package com.jack.hedoncalculator.controller;

import com.jack.hedoncalculator.model.User;
import com.jack.hedoncalculator.service.AuthService;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UUID> login(@RequestParam String username, @RequestParam String password) {
        UUID sessionToken = authService.login(username, password);
        if (sessionToken != null) {
            return ResponseEntity.ok(sessionToken);
        }
        System.out.println("failed to log in");
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestParam UUID sessionToken) {
        authService.logout(sessionToken);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateSession(@RequestParam UUID sessionToken) {
        return ResponseEntity.ok(authService.validateSession(sessionToken));
    }
    
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestParam String username, @RequestParam String password) {
        try {
            User newUser = authService.createUser(username, password);
            return ResponseEntity.ok(newUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Service is up!");
    }
}
