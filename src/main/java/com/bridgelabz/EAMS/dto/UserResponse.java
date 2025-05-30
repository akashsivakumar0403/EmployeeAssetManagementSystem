package com.bridgelabz.EAMS.dto;
import com.bridgelabz.EAMS.entity.UserRole;

public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private UserRole role;

    // Constructor
    public UserResponse(Long id, String name, String email, UserRole role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    // Getters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public UserRole getRole() {
        return role;
    }
}
