package com.ManagementEmployeeOfficeService.controller;

import com.ManagementEmployeeOfficeService.dto.JwtResponse;
import com.ManagementEmployeeOfficeService.dto.LoginRequest;
import com.ManagementEmployeeOfficeService.entity.User;
import com.ManagementEmployeeOfficeService.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/validate-token")
    public boolean validateToken(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            return authService.validateToken(token);
        }
        return false;
    }
}
