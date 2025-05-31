//package com.bridgelabz.EAMS.service;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import com.bridgelabz.EAMS.dto.LoginRequest;
//import com.bridgelabz.EAMS.dto.RegistrationRequest;
//import com.bridgelabz.EAMS.dto.UserResponse;
//import com.bridgelabz.EAMS.entity.User;
//import com.bridgelabz.EAMS.entity.UserRole;
//import com.bridgelabz.EAMS.exception.UserException;
//import com.bridgelabz.EAMS.repository.UserRepository;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.*;
//import java.util.*;
//
//class UserServiceImplTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private UserServiceImpl userService;
//
//    @BeforeEach
//    void setup() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void registerUser_success() {
//        RegistrationRequest request = new RegistrationRequest("Alice", "alice@example.com", "password", UserRole.OPERATOR);
//        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
//
//        User savedUser = new User(request.getName(), request.getEmail(), request.getPassword(), request.getRole());
//        savedUser.setId(1L);
//        when(userRepository.save(any(User.class))).thenReturn(savedUser);
//
//        UserResponse response = userService.registerUser(request);
//
//        assertEquals(1L, response.getId());
//        assertEquals("Alice", response.getName());
//        assertEquals("alice@example.com", response.getEmail());
//        assertEquals(UserRole.OPERATOR, response.getRole());
//        verify(userRepository).findByEmail(request.getEmail());
//        verify(userRepository).save(any(User.class));
//    }
//
//    @Test
//    void registerUser_duplicateEmail_throwsException() {
//        RegistrationRequest request = new RegistrationRequest("Bob", "bob@example.com", "password", UserRole.MANAGER);
//        User existingUser = new User("Bob", "bob@example.com", "password", UserRole.MANAGER);
//
//        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(existingUser));
//
//        UserException ex = assertThrows(UserException.class, () -> userService.registerUser(request));
//        assertTrue(ex.getMessage().contains("User already exists"));
//        verify(userRepository).findByEmail(request.getEmail());
//        verify(userRepository, never()).save(any());
//    }
//
//    @Test
//    void loginUser_success() {
//        LoginRequest request = new LoginRequest("charlie@example.com", "password123");
//        User existingUser = new User("Charlie", request.getEmail(), "password123", UserRole.TECHNICIAN);
//        existingUser.setId(2L);
//
//        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(existingUser));
//
//        UserResponse response = userService.loginUser(request);
//
//        assertEquals(2L, response.getId());
//        assertEquals("Charlie", response.getName());
//        assertEquals(request.getEmail(), response.getEmail());
//        assertEquals(UserRole.TECHNICIAN, response.getRole());
//    }
//
//    @Test
//    void loginUser_wrongPassword_throwsException() {
//        LoginRequest request = new LoginRequest("dave@example.com", "wrongpass");
//        User user = new User("Dave", request.getEmail(), "correctpass", UserRole.OPERATOR);
//        user.setId(3L);
//
//        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
//
//        UserException ex = assertThrows(UserException.class, () -> userService.loginUser(request));
//        assertTrue(ex.getMessage().contains("Invalid email or password"));
//    }
//
//    @Test
//    void loginUser_emailNotFound_throwsException() {
//        LoginRequest request = new LoginRequest("noone@example.com", "password");
//
//        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
//
//        UserException ex = assertThrows(UserException.class, () -> userService.loginUser(request));
//        assertTrue(ex.getMessage().contains("Invalid email or password"));
//    }
//
//    @Test
//    void getAllUsers_returnsList() {
//        List<User> users = Arrays.asList(
//                new User("Alice", "alice@example.com", "pass1", UserRole.MANAGER),
//                new User("Bob", "bob@example.com", "pass2", UserRole.OPERATOR)
//        );
//        users.get(0).setId(1L);
//        users.get(1).setId(2L);
//
//        when(userRepository.findAll()).thenReturn(users);
//
//        List<UserResponse> responseList = userService.getAllUsers();
//
//        assertEquals(2, responseList.size());
//        assertEquals("Alice", responseList.get(0).getName());
//        assertEquals("bob@example.com", responseList.get(1).getEmail());
//    }
//
//    @Test
//    void updateUserRole_success() {
//        User user = new User("Eve", "eve@example.com", "pass", UserRole.OPERATOR);
//        user.setId(10L);
//        User updatedUser = new User("Eve", "eve@example.com", "pass", UserRole.MANAGER);
//        updatedUser.setId(10L);
//
//        when(userRepository.findById(10L)).thenReturn(Optional.of(user));
//        when(userRepository.save(any(User.class))).thenReturn(updatedUser);
//
//        UserResponse response = userService.updateUserRole(10L, UserRole.MANAGER);
//
//        assertEquals(10L, response.getId());
//        assertEquals(UserRole.MANAGER, response.getRole());
//        verify(userRepository).findById(10L);
//        verify(userRepository).save(any(User.class));
//    }
//
//    @Test
//    void updateUserRole_userNotFound_throwsException() {
//        when(userRepository.findById(999L)).thenReturn(Optional.empty());
//
//        UserException ex = assertThrows(UserException.class, () -> userService.updateUserRole(999L, UserRole.TECHNICIAN));
//        assertTrue(ex.getMessage().contains("User not found"));
//    }
//}
