package com.eams.controller;

import com.eams.dto.RegistrationRequest;
import com.eams.entity.User;
import com.eams.repository.IUserRepository;
import com.eams.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private IUserRepository userRepository;


    @PostMapping("/login")
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
            return "Invalid role. Allowed values: MANAGER, OPERATOR.";
        }
        User user = new User(request.getName(), request.getUsername(), request.getPassword(), role);
        userRepository.save(user);
        return "Registration successful!";
    }
}
