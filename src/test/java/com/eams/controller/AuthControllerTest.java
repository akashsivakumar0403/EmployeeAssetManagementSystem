package com.eams.controller;

import com.eams.dto.LoginRequest;
import com.eams.dto.RegistrationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private RegistrationRequest registration;

    @BeforeEach
    void setup() {
        registration = new RegistrationRequest();
        registration.setName("Test User");
        registration.setUsername("testuser");
        registration.setPassword("password123");
        registration.setRole("MANAGER");
    }

    @Test
    void testRegisterSuccess() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registration)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Registration successful")));
    }

    @Test
    void testRegisterDuplicate() throws Exception {
        // Register once
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registration)))
                .andExpect(status().isOk());

        // Register again
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registration)))
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString("Username already exists")));
    }

    @Test
    void testLoginSuccess() throws Exception {
        // Ensure user is registered
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registration)))
                .andExpect(status().isOk());

        LoginRequest login = new LoginRequest();
        login.setUsername("testuser");
        login.setPassword("password123");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andExpect(content().string("Login successful!"));
    }

    @Test
    void testLoginInvalid() throws Exception {
        LoginRequest login = new LoginRequest();
        login.setUsername("nonexistent");
        login.setPassword("wrong");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid username or password."));
    }

    @Test
    void testRoleDescriptionInvalid() throws Exception {
        mockMvc.perform(get("/api/auth/role-description/INVALID"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid role type."));
    }
}
