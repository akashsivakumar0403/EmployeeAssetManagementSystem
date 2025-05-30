package com.bridgelabz.EAMS.dto;

import com.bridgelabz.EAMS.entity.UserRole;

public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private UserRole role;

    // No-args constructor
    public UserResponse() {}

    // All-args constructor
    public UserResponse(Long id, String name, String email, UserRole role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public UserRole getRole() { return role; }

    // Setters (needed by Jackson)
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setRole(UserRole role) { this.role = role; }
}
