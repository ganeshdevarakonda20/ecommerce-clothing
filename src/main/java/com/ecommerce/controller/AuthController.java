package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.AuthResponseDto;
import com.ecommerce.dto.LoginRequestDto;
import com.ecommerce.dto.UserRequestDto;
import com.ecommerce.dto.UserResponseDto;
import com.ecommerce.entity.User;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.security.JwtUtils;
import com.ecommerce.service.UserService;

import jakarta.validation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;
    // 1. Register a new user
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserRequestDto dto) {
        UserResponseDto createdUser = userService.addUser(dto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    // 2. Login and get JWT token
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto loginDto) {
        
        // A. Ask Spring Security's AuthenticationManager to verify email & password
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        
        // B. Fetch the user from DB to get their details (ID and role)
        User user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + loginDto.getEmail()));
        String roleName = (user.getRole() != null) ? user.getRole().getRoleName() : "CUSTOMER";
        
        // C. Generate the JWT Token!
        String token = jwtUtils.generateToken(user.getEmail(), roleName, user.getUserId());
        
        // D. Build the response DTO
        AuthResponseDto response = new AuthResponseDto(
                token,
                user.getUserId(),
                user.getEmail(),
                roleName,
                "Login successful!"
        );
        return ResponseEntity.ok(response);
    }
	
}
