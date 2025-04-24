package com.ManagementEmployeeOfficeService.controller;

import com.ManagementEmployeeOfficeService.dto.JwtResponse;
import com.ManagementEmployeeOfficeService.dto.LoginRequest;
import com.ManagementEmployeeOfficeService.entity.User;
import com.ManagementEmployeeOfficeService.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private LoginRequest loginRequest;
    private User user;
    private JwtResponse jwtResponse;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequest();
        loginRequest.setEmail("eleazar@nttdata.com");
        loginRequest.setPassword("password123");

        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("123456");
        user.setEmail("eleazar@nttdata.com");

        jwtResponse = new JwtResponse("testToken", user);
    }

    @Test
    @DisplayName("Should successfully login user and return JWT response")
    void loginTest() {
        when(authService.login(any(LoginRequest.class))).thenReturn(jwtResponse);

        JwtResponse response = authController.login(loginRequest);

        assertNotNull(response);
        assertEquals("testToken", response.getToken());
        assertEquals(user, response.getUser());
        verify(authService, times(1)).login(loginRequest);
    }

    @Test
    @DisplayName("Should register a new user and return user object")
    void registerTest() {
        when(authService.register(any(User.class))).thenReturn(user);

        User registeredUser = authController.register(user);

        assertNotNull(registeredUser);
        assertEquals(1L, registeredUser.getId());
        assertEquals("testuser", registeredUser.getUsername());
        verify(authService, times(1)).register(user);
    }

    @Test
    @DisplayName("Should return true when token is valid")
    void validateTokenValidTest() {
        String authHeader = "Bearer validToken";
        when(authService.validateToken("validToken")).thenReturn(true);

        boolean result = authController.validateToken(authHeader);

        assertTrue(result);
        verify(authService, times(1)).validateToken("validToken");
    }

    @Test
    @DisplayName("Should return false when token is invalid")
    void validateTokenInvalidTest() {
        String authHeader = "Bearer invalidToken";
        when(authService.validateToken("invalidToken")).thenReturn(false);

        boolean result = authController.validateToken(authHeader);

        assertFalse(result);
        verify(authService, times(1)).validateToken("invalidToken");
    }

    @Test
    @DisplayName("Should return false when authorization header is null")
    void validateTokenNullHeaderTest() {
        boolean result = authController.validateToken(null);

        assertFalse(result);
        verify(authService, never()).validateToken(anyString());
    }

    @Test
    @DisplayName("Should return false when authorization header has invalid format")
    void validateTokenInvalidHeaderFormatTest() {
        String authHeader = "InvalidFormat token123";

        boolean result = authController.validateToken(authHeader);

        assertFalse(result);
        verify(authService, never()).validateToken(anyString());
    }
}