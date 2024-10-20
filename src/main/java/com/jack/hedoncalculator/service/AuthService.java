package com.jack.hedoncalculator.service;

import com.jack.hedoncalculator.model.User;
import com.jack.hedoncalculator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UUID login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) {
            User user = userOpt.get();
            UUID sessionToken = UUID.randomUUID();
            user.setSessionToken(sessionToken);
            userRepository.save(user);
            return sessionToken;
        }
        return null;
    }

    public boolean validateSession(UUID sessionToken) {
        return userRepository.findBySessionToken(sessionToken).isPresent();
    }

    public void logout(UUID sessionToken) {
        userRepository.findBySessionToken(sessionToken).ifPresent(user -> {
            user.setSessionToken(null);
            userRepository.save(user);
        });
    }
    
    public User createUser(String username, String rawPassword) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(rawPassword));
        return userRepository.save(newUser);
    }
}
