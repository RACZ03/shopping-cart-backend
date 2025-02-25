package com.technicaltest.userservice.app.serviceImpl;

import com.technicaltest.userservice.app.dto.UserRegistrationRequest;
import com.technicaltest.userservice.app.dto.UserLoginRequest;
import com.technicaltest.userservice.app.dto.UserResponseDTO;
import com.technicaltest.userservice.app.model.entity.UserEntity;
import com.technicaltest.userservice.app.model.repository.UserRepository;
import com.technicaltest.userservice.app.service.UserService;
import com.technicaltest.userservice.exception.UserNotFoundException;
import com.technicaltest.userservice.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserResponseDTO registerUser(UserRegistrationRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }
        UserEntity userEntity = UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .address(request.getAddress())
                .email(request.getEmail())
                .dateOfBirth(request.getDateOfBirth())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userEntity = userRepository.save(userEntity);
        return convertToDTO(userEntity);
    }

    @Override
    public String loginUser(UserLoginRequest request) {
        UserEntity userEntity = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        if (!passwordEncoder.matches(request.getPassword(), userEntity.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        return jwtTokenProvider.generateToken(userEntity.getEmail());
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
        return convertToDTO(userEntity);
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserRegistrationRequest request) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));

        userEntity.setFirstName(request.getFirstName());
        userEntity.setLastName(request.getLastName());
        userEntity.setAddress(request.getAddress());
        userEntity.setEmail(request.getEmail());
        userEntity.setDateOfBirth(request.getDateOfBirth());

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        userEntity = userRepository.save(userEntity);
        return convertToDTO(userEntity);
    }


    private UserResponseDTO convertToDTO(UserEntity userEntity) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(userEntity.getId());
        dto.setFirstName(userEntity.getFirstName());
        dto.setLastName(userEntity.getLastName());
        dto.setAddress(userEntity.getAddress());
        dto.setEmail(userEntity.getEmail());
        dto.setDateOfBirth(userEntity.getDateOfBirth());
        return dto;
    }
}
