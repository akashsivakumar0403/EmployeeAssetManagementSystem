package com.bridgelabz.EAMS.service;
import com.bridgelabz.EAMS.dto.LoginRequest;
import com.bridgelabz.EAMS.dto.RegistrationRequest;
import com.bridgelabz.EAMS.dto.UserResponse;
import com.bridgelabz.EAMS.entity.User;
import com.bridgelabz.EAMS.repository.UserRepository;
import com.bridgelabz.EAMS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponse registerUser(RegistrationRequest request) {
        // Check if user already exists by email
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("User already exists with email: " + request.getEmail());
        }

        // Create new user (password stored as plain text here, ideally encode)
        User user = new User(
                request.getName(),
                request.getEmail(),
                request.getPassword(), // TODO: Use BCryptPasswordEncoder for password encoding
                request.getRole()
        );

        userRepository.save(user);

        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }

    @Override
    public UserResponse loginUser(LoginRequest request) {
        // Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // Check password match (plain text here, ideally encode + match)
        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }
}
