package com.productapp.backend.controller;

import com.productapp.backend.dto.AuthResponse;
import com.productapp.backend.dto.LoginRequest;
import com.productapp.backend.dto.RegisterRequest;
import com.productapp.backend.entity.Role;
import com.productapp.backend.entity.User;
import com.productapp.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());
        if (userOpt.isEmpty() || !passwordEncoder.matches(request.getPassword(), userOpt.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        User user = userOpt.get();
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
