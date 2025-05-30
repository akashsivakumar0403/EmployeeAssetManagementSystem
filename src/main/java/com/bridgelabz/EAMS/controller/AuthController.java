package com.bridgelabz.EAMS.controller;

import com.bridgelabz.EAMS.dto.LoginRequest;
import com.bridgelabz.EAMS.dto.RegistrationRequest;
import com.bridgelabz.EAMS.dto.UserResponse;
import com.bridgelabz.EAMS.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;  // <-- Add this import
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Authentication", description = "User Registration and Login APIs")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<String> registerUser(@RequestBody @Valid RegistrationRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok("User registered with role: " + request.getRole());
    }

    @PostMapping("/login")
    @Operation(summary = "Login an existing user")
    public UserResponse login(@Valid @RequestBody LoginRequest request) {
        return userService.loginUser(request);
    }

}
