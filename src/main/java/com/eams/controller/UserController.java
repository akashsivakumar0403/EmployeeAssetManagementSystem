package com.eams.controller;

import com.eams.dto.RoleUpdateRequest;
import com.eams.dto.UserDto;
import com.eams.entity.User;
import com.eams.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid; 

import java.util.List;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('MANAGER')")  // Only accessible to MANAGER role
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<String> updateUserRole(
            @PathVariable Integer id,
            @Valid @RequestBody RoleUpdateRequest request 
    ) {
        try {
            User.Role.valueOf(((String) request.getRole()).toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid role. Allowed values: MANAGER, OPERATOR, ADMIN.");
        }
        userService.updateUserRole(id, request.getRole());
        return ResponseEntity.ok("User role updated successfully.");
    }

}
