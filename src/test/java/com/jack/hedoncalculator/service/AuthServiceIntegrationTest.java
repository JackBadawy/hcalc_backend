package com.jack.hedoncalculator.service;

import com.jack.hedoncalculator.model.User;
import com.jack.hedoncalculator.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AuthServiceIntegrationTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private static final String TEST_USERNAME = "testuser";
    private static final String TEST_PASSWORD = "testpassword";

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void testCreateUser() {
        User newUser = authService.createUser(TEST_USERNAME, TEST_PASSWORD);

        assertNotNull(newUser);
        assertEquals(TEST_USERNAME, newUser.getUsername());
        assertTrue(passwordEncoder.matches(TEST_PASSWORD, newUser.getPassword()));
    }

    @Test
    void testCreateDuplicateUser() {
        authService.createUser(TEST_USERNAME, TEST_PASSWORD);

        assertThrows(IllegalArgumentException.class, () -> {
            authService.createUser(TEST_USERNAME, "anotherpassword");
        });
    }

    @Test
    void testLoginSuccess() {
        authService.createUser(TEST_USERNAME, TEST_PASSWORD);

        UUID sessionToken = authService.login(TEST_USERNAME, TEST_PASSWORD);

        assertNotNull(sessionToken);
        assertTrue(authService.validateSession(sessionToken));
    }

    @Test
    void testLoginFailure() {
        authService.createUser(TEST_USERNAME, TEST_PASSWORD);

        UUID sessionToken = authService.login(TEST_USERNAME, "wrongpassword");

        assertNull(sessionToken);
    }

    @Test
    void testLogout() {
        authService.createUser(TEST_USERNAME, TEST_PASSWORD);
        UUID sessionToken = authService.login(TEST_USERNAME, TEST_PASSWORD);

        authService.logout(sessionToken);

        assertFalse(authService.validateSession(sessionToken));
    }

    @Test
    void testValidateSession() {
        authService.createUser(TEST_USERNAME, TEST_PASSWORD);
        UUID sessionToken = authService.login(TEST_USERNAME, TEST_PASSWORD);

        assertTrue(authService.validateSession(sessionToken));
        assertFalse(authService.validateSession(UUID.randomUUID()));
    }
}