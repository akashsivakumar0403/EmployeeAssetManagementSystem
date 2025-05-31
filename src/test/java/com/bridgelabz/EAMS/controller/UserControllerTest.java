package com.bridgelabz.EAMS.controller;

import com.bridgelabz.EAMS.dto.RoleUpdateRequest;
import com.bridgelabz.EAMS.dto.UserResponse;
import com.bridgelabz.EAMS.entity.UserRole;
import com.bridgelabz.EAMS.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private HttpSession session;

    @InjectMocks
    private UserController userController;

    @Test
    void getAllUsers_accessDenied_whenNoUserInSession() {
        when(session.getAttribute("loggedInUser")).thenReturn(null);

        ResponseEntity<?> response = userController.getAllUsers(session);

        assertEquals(403, response.getStatusCodeValue());
        assertEquals("Access denied: Manager only", response.getBody());
    }

    @Test
    void getAllUsers_accessDenied_whenUserNotManager() {
        UserResponse user = new UserResponse(1L, "John", "john@example.com", UserRole.OPERATOR);
        when(session.getAttribute("loggedInUser")).thenReturn(user);

        ResponseEntity<?> response = userController.getAllUsers(session);

        assertEquals(403, response.getStatusCodeValue());
        assertEquals("Access denied: Manager only", response.getBody());
    }

    @Test
    void getAllUsers_success() {
        UserResponse manager = new UserResponse(2L, "Manager", "manager@example.com", UserRole.MANAGER);
        when(session.getAttribute("loggedInUser")).thenReturn(manager);

        List<UserResponse> mockUsers = List.of(
                new UserResponse(1L, "John", "john@example.com", UserRole.OPERATOR),
                new UserResponse(3L, "Alice", "alice@example.com", UserRole.TECHNICIAN)
        );
        when(userService.getAllUsers()).thenReturn(mockUsers);

        ResponseEntity<?> response = userController.getAllUsers(session);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockUsers, response.getBody());
    }

    @Test
    void updateUserRole_accessDenied_whenNoUserInSession() {
        when(session.getAttribute("loggedInUser")).thenReturn(null);

        ResponseEntity<?> response = userController.updateUserRole(1L, new RoleUpdateRequest(), session);

        assertEquals(403, response.getStatusCodeValue());
        assertEquals("Access denied: Manager only", response.getBody());
    }

    @Test
    void updateUserRole_accessDenied_whenUserNotManager() {
        UserResponse user = new UserResponse(1L, "John", "john@example.com", UserRole.OPERATOR);
        when(session.getAttribute("loggedInUser")).thenReturn(user);

        ResponseEntity<?> response = userController.updateUserRole(1L, new RoleUpdateRequest(), session);

        assertEquals(403, response.getStatusCodeValue());
        assertEquals("Access denied: Manager only", response.getBody());
    }

    @Test
    void updateUserRole_success() {
        UserResponse manager = new UserResponse(2L, "Manager", "manager@example.com", UserRole.MANAGER);
        when(session.getAttribute("loggedInUser")).thenReturn(manager);

        RoleUpdateRequest roleRequest = new RoleUpdateRequest();
        roleRequest.setRole(UserRole.TECHNICIAN);

        UserResponse updatedUser = new UserResponse(1L, "John", "john@example.com", UserRole.TECHNICIAN);
        when(userService.updateUserRole(1L, UserRole.TECHNICIAN)).thenReturn(updatedUser);

        ResponseEntity<?> response = userController.updateUserRole(1L, roleRequest, session);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedUser, response.getBody());
    }
}
