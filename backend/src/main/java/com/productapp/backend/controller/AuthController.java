package com.productapp.backend.controller;

import com.productapp.backend.dto.AuthResponse;
import com.productapp.backend.dto.LoginRequest;
import com.productapp.backend.dto.RegisterRequest;
import com.productapp.backend.entity.Role;
import com.productapp.backend.entity.User;
import com.productapp.backend.repository.UserRepository;
import com.productapp.backend.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(new AuthResponse("dummy-token", user.getUsername(), user.getRole().name()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }

        Role role = request.getRole() != null && request.getRole().equalsIgnoreCase("ADMIN")
            ? Role.ADMIN : Role.USER;

        User user = new User(null, request.getUsername(), passwordEncoder.encode(request.getPassword()),
            request.getEmail(), role);
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }
}
