package com.bridgelabz.EAMS.service;
import java.util.List;

import com.bridgelabz.EAMS.dto.LoginRequest;
import com.bridgelabz.EAMS.dto.RegistrationRequest;
import com.bridgelabz.EAMS.dto.UserResponse;
import com.bridgelabz.EAMS.entity.UserRole;

public interface IUserService {
    UserResponse registerUser(RegistrationRequest request);
    UserResponse loginUser(LoginRequest request);
    List<UserResponse> getAllUsers();
    UserResponse updateUserRole(Long id, UserRole newRole);
}
