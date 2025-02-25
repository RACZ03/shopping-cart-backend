package com.technicaltest.userservice.app.service;

import com.technicaltest.userservice.app.dto.UserRegistrationRequest;
import com.technicaltest.userservice.app.dto.UserLoginRequest;
import com.technicaltest.userservice.app.dto.UserResponseDTO;

public interface UserService {
    UserResponseDTO registerUser(UserRegistrationRequest request);
    String loginUser(UserLoginRequest request);
    UserResponseDTO getUserById(Long id);
    UserResponseDTO updateUser(Long id, UserRegistrationRequest request);
}
