package com.eams.controller;

import com.eams.dto.LoginRequest;
import com.eams.dto.RegistrationRequest;
import com.eams.entity.User;
import com.eams.repository.IUserRepository;
import com.eams.repository.IRoleDescriptionRepository;
import com.eams.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleDescriptionRepository roleDescriptionRepository;

    @Operation(summary = "Login with username and password")
    @ApiResponse(responseCode = "200", description = "Login result",
            content = @Content(mediaType = "text/plain"))
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Login details",
                    required = true,
                    content = @Content(schema = @Schema(implementation = LoginRequest.class)))
            @Valid @RequestBody LoginRequest loginRequest) {

        boolean success = loginService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return success ?
                ResponseEntity.ok("Login successful!") :
                ResponseEntity.status(401).body("Invalid username or password.");
    }

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registration result",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "409", description = "Username already exists",
                    content = @Content)
    })
    @PostMapping("/register")

    public ResponseEntity<String> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Registration details",
                    required = true,
                    content = @Content(schema = @Schema(implementation = RegistrationRequest.class)))
            @Valid @RequestBody RegistrationRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists.");

        }

        User.Role role;
        try {
            role = User.Role.valueOf(request.getRole().toUpperCase());
        } catch (IllegalArgumentException ex) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid role. Allowed values: MANAGER, OPERATOR, ADMIN.");

        }

        User user = new User(request.getName(), request.getUsername(), request.getPassword(), role);
        userRepository.save(user);
        return ResponseEntity.ok("Registration successful!");
    }



    @Operation(summary = "Get responsibilities for a specific role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role responsibilities",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid role type",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Role description not found",
                    content = @Content)
    })
    @GetMapping("/role-description/{role}")
    public ResponseEntity<?> getRoleResponsibilities(
            @PathVariable String role) {
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