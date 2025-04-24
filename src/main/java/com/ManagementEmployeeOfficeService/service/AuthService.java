package com.ManagementEmployeeOfficeService.service;

import com.ManagementEmployeeOfficeService.dto.JwtResponse;
import com.ManagementEmployeeOfficeService.dto.LoginRequest;
import com.ManagementEmployeeOfficeService.entity.User;
import com.ManagementEmployeeOfficeService.repository.UserRepository;
import com.ManagementEmployeeOfficeService.security.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private String secretKey = "your-secret-key";
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder encoder;

    public JwtResponse login(LoginRequest request) {
        try {
            System.out.println("Intentando autenticar usuario: " + request.getEmail());

            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            System.out.println("Usuario autenticado correctamente: " + userDetails.getUsername());

            String token = jwtUtils.generateToken(userDetails);
            System.out.println("Token generado correctamente");
            User user = userRepo.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            return new JwtResponse(token, user);

        } catch (Exception e) {
            System.err.println("Error en autenticación: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public User register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
}