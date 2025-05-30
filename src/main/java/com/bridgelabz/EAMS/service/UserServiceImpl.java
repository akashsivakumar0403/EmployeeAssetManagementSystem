package com.bridgelabz.EAMS.service;

import com.bridgelabz.EAMS.dto.LoginRequest;
import com.bridgelabz.EAMS.dto.RegistrationRequest;
import com.bridgelabz.EAMS.dto.UserResponse;
import com.bridgelabz.EAMS.entity.User;
import com.bridgelabz.EAMS.entity.UserRole;
import com.bridgelabz.EAMS.exception.UserException;
import com.bridgelabz.EAMS.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponse registerUser(RegistrationRequest request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new UserException("User already exists with email: " + request.getEmail());
        }

        // Create and save user (password in plain text as per your instruction)
        User user = new User(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getRole()
        );

        userRepository.save(user);

        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }

    @Override
    public UserResponse loginUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserException("Invalid email or password"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new UserException("Invalid email or password");
        }

        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }

	@Override
	public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole()))
                .collect(Collectors.toList());
    }

	@Override
	public UserResponse updateUserRole(Long id, UserRole newRole) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException("User not found with id: " + id));
        user.setRole(newRole);
        User updatedUser = userRepository.save(user);
        User refreshedUser = userRepository.findById(updatedUser.getId()).orElseThrow();
        return new UserResponse(refreshedUser.getId(), refreshedUser.getName(), refreshedUser.getEmail(), refreshedUser.getRole());
    }
}
