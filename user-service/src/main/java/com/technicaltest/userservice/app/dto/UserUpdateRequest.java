package com.technicaltest.userservice.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserUpdateRequest {
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Address is required")
    private String address;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    private String email;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;
}
