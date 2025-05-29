package com.eams.service;

import com.eams.dto.RegistrationRequest;
import com.eams.entity.User;
import com.eams.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String register(RegistrationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return "Username already exists.";
        }

        String roleString = request.getRole();
        if (roleString == null || roleString.trim().isEmpty()) {
            return "Role is required.";
        }

        User.Role role;
        try {
            role = User.Role.valueOf(roleString.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            return "Invalid role. Allowed values: MANAGER, OPERATOR.";
        }

        // Prevent unauthorized ADMIN registration if needed
        if (role == User.Role.ADMIN) {
            return "You cannot assign ADMIN role during registration.";
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getName(), request.getUsername(), encodedPassword, role);
        userRepository.save(user);
        return "Registration successful!";
    }
}
