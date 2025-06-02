package com.bridgelabz.EAMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.EAMS.dto.RoleUpdateRequest;
import com.bridgelabz.EAMS.dto.UserResponse;
import com.bridgelabz.EAMS.entity.UserRole;
import com.bridgelabz.EAMS.service.IUserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
@RestController
@RequestMapping("/api/users")
@Tag(name = "Manager Only", description = "View users and role assignment")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers(HttpSession session) {
        UserResponse loggedInUser = (UserResponse) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != UserRole.MANAGER) {
            return ResponseEntity.status(403).body("Access denied: Manager only");
        }
        // Now call service to get all users (you may need to add this method)
        List<UserResponse> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<?> updateUserRole(@PathVariable Long id, @RequestBody RoleUpdateRequest request, HttpSession session) {
        UserResponse loggedInUser = (UserResponse) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != UserRole.MANAGER) {
            return ResponseEntity.status(403).body("Access denied: Manager only");
        }
        UserResponse updatedUser = userService.updateUserRole(id, request.getRole());
        return ResponseEntity.ok(updatedUser);
    }
}

