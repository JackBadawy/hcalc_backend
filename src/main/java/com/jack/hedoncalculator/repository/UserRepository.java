package com.jack.hedoncalculator.repository;

import com.jack.hedoncalculator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findBySessionToken(UUID sessionToken);
}