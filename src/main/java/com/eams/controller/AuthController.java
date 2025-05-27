package com.eams.controller;

import com.eams.dto.RegistrationRequest;
import com.eams.entity.User;
import com.eams.repository.IUserRepository;
import com.eams.repository.IRoleDescriptionRepository;
import com.eams.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleDescriptionRepository roleDescriptionRepository;

    @GetMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        boolean success = loginService.login(username, password);
        if (success) {
            return "Login successful!";
        } else {
            return "Invalid username or password.";
        }
    }

    @PostMapping("/register")
    public String register(@RequestBody RegistrationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return "Username already exists.";
        }
        User.Role role;
        try {
            role = User.Role.valueOf(request.getRole().toUpperCase());
        } catch (IllegalArgumentException ex) {
            return "Invalid role. Allowed values: MANAGER, OPERATOR, ADMIN.";
        }
        User user = new User(request.getName(), request.getUsername(), request.getPassword(), role);
        userRepository.save(user);
        return "Registration successful!";
    }
    @GetMapping("/role-description/{role}")
    public ResponseEntity<?> getRoleResponsibilities(@PathVariable String role) {
        try {
            User.Role userRole = User.Role.valueOf(role.toUpperCase());
            return roleDescriptionRepository.findByRole(userRole)
                    .map(desc -> ResponseEntity.ok(desc.getResponsibilities()))
                    .orElse(ResponseEntity.status(404).body("Role description not found."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid role type.");
        }
    }
}
