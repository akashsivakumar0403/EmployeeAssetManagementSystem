package com.bridgelabz.EAMS.dto;

import com.bridgelabz.EAMS.entity.UserRole;

public class RoleUpdateRequest {
    private UserRole role;

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }
}
