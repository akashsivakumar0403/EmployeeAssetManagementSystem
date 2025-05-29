package com.eams.service;

import com.eams.dto.UserDto;
import com.eams.entity.User;
import com.eams.repository.IUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getName(),
                        user.getUsername(),  
                        user.getRole()
                ))
                .collect(Collectors.toList());
    }

    public void updateUserRole(int id, String roleStr) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        if (roleStr == null || roleStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Role must not be empty");
        }

        try {
            User.Role role = User.Role.valueOf(roleStr.trim().toUpperCase());
            user.setRole(role);
            userRepository.save(user);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid role: must be MANAGER, OPERATOR, or ADMIN");
        }
    }
}
