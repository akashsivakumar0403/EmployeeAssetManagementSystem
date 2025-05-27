package com.eams.service;

import com.eams.dto.UserDto;
import com.eams.entity.User;
import com.eams.repository.IUserRepository;
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

    
    public void updateUserRole(int id, Object object) {
        User user = userRepository.findById(id) 
                .orElseThrow(() -> new RuntimeException("User not found"));

        try {
            user.setRole(Enum.valueOf(User.Role.class, ((String) object).toUpperCase()));
            userRepository.save(user);
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Invalid role: must be MANAGER, OPERATOR, or ADMIN");
        }
    }
}
