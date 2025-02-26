package com.technicaltest.userservice.app.controller;

import com.technicaltest.userservice.app.dto.ApiResponse;
import com.technicaltest.userservice.app.dto.LoginResponseDTO;
import com.technicaltest.userservice.app.dto.UserLoginRequest;
import com.technicaltest.userservice.app.dto.UserRegistrationRequest;
import com.technicaltest.userservice.app.dto.UserResponseDTO;
import com.technicaltest.userservice.app.dto.UserUpdateRequest;
import com.technicaltest.userservice.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDTO>> register(@Valid @RequestBody UserRegistrationRequest request) {
        UserResponseDTO response = userService.registerUser(request);
        ApiResponse<UserResponseDTO> apiResponse = new ApiResponse<>(HttpStatus.CREATED.value(), "User registered successfully", response);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@Valid @RequestBody UserLoginRequest request) {
        String token = userService.loginUser(request);
        LoginResponseDTO loginResponse = new LoginResponseDTO(token);
        ApiResponse<LoginResponseDTO> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "Login successful", loginResponse);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(authentication.getName());
        UserResponseDTO response = userService.getUserById(userId);
        ApiResponse<UserResponseDTO> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "User fetched successfully", response);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest request) {
        UserResponseDTO response = userService.updateUser(id, request);
        ApiResponse<UserResponseDTO> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "User updated successfully", response);
        return ResponseEntity.ok(apiResponse);
    }
}
