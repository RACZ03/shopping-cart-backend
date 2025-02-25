package com.technicaltest.userservice.app.controller;

import com.technicaltest.userservice.app.dto.ApiResponse;
import com.technicaltest.userservice.app.dto.LoginResponseDTO;
import com.technicaltest.userservice.app.dto.UserRegistrationRequest;
import com.technicaltest.userservice.app.dto.UserLoginRequest;
import com.technicaltest.userservice.app.dto.UserResponseDTO;
import com.technicaltest.userservice.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

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

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUser(@PathVariable Long id) {
        UserResponseDTO response = userService.getUserById(id);
        ApiResponse<UserResponseDTO> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "User fetched successfully", response);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(@PathVariable Long id, @Valid @RequestBody UserRegistrationRequest request) {
        UserResponseDTO response = userService.updateUser(id, request);
        ApiResponse<UserResponseDTO> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "User updated successfully", response);
        return ResponseEntity.ok(apiResponse);
    }
}
