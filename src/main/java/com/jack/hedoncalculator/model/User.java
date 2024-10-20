package com.jack.hedoncalculator.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "userh")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private UUID sessionToken;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UUID getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(UUID sessionToken) {
		this.sessionToken = sessionToken;
	}
}