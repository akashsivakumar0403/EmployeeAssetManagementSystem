package com.bridgelabz.EAMS.controller;

import com.bridgelabz.EAMS.dto.LoginRequest;
import com.bridgelabz.EAMS.dto.RegistrationRequest;
import com.bridgelabz.EAMS.dto.UserResponse;
import com.bridgelabz.EAMS.entity.UserRole;
import com.bridgelabz.EAMS.service.IUserService;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private IUserService userService;

    @Mock
    private HttpSession session;

    @InjectMocks
    private AuthController authController;

    @Test
    void testRegisterUser_Success() {
        RegistrationRequest request = new RegistrationRequest();
        request.setName("John");
        request.setEmail("john@example.com");
        request.setPassword("pass123");
        request.setRole(UserRole.MANAGER);

        // FIXED: registerUser is a void method
        doNothing().when(userService).registerUser(any(RegistrationRequest.class));


        ResponseEntity<String> response = authController.registerUser(request);

        assertEquals("User registered with role: MANAGER", response.getBody());
        verify(userService).registerUser(request);
    }


    @Test
    void testRegisterUser_InvalidEmail() {
        RegistrationRequest request = new RegistrationRequest();
        request.setName("Jane");
        request.setEmail("invalid-email");
        request.setPassword("password123");
        request.setRole(UserRole.OPERATOR);

        doThrow(new IllegalArgumentException("Invalid email format"))
                .when(userService).registerUser(request);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authController.registerUser(request);
        });

        assertEquals("Invalid email format", exception.getMessage());
        verify(userService).registerUser(request);
    }

    @Test
    void testLogin_Success() {
        LoginRequest request = new LoginRequest("john@example.com", "pass123");

        UserResponse mockResponse = new UserResponse(1L, "John", "john@example.com", UserRole.OPERATOR);
        when(userService.loginUser(request)).thenReturn(mockResponse);

        ResponseEntity<UserResponse> response = authController.login(request, session);

        assertEquals(mockResponse, response.getBody());
        verify(userService).loginUser(request);
        verify(session).setAttribute("loggedInUser", mockResponse);
    }

    @Test
    void testLogin_InvalidCredentials() {
        LoginRequest request = new LoginRequest("john@example.com", "wrongpass");

        when(userService.loginUser(request))
                .thenThrow(new RuntimeException("Invalid credentials"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authController.login(request, session);
        });

        assertEquals("Invalid credentials", exception.getMessage());
        verify(userService).loginUser(request);
        verifyNoInteractions(session);
    }
}
