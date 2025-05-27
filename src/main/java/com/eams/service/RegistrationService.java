package com.eams.service;

import com.eams.dto.RegistrationRequest;
import com.eams.entity.User;
import com.eams.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    @Autowired
    private IUserRepository userRepository;

    public String register(RegistrationRequest request) {
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
