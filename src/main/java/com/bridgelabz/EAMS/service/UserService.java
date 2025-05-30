package com.bridgelabz.EAMS.service;
import com.bridgelabz.EAMS.dto.LoginRequest;
import com.bridgelabz.EAMS.dto.RegistrationRequest;
import com.bridgelabz.EAMS.dto.UserResponse;

public interface UserService {
    UserResponse registerUser(RegistrationRequest request);
    UserResponse loginUser(LoginRequest request);
}
