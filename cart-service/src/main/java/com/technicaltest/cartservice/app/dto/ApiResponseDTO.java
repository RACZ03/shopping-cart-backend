package com.technicaltest.cartservice.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponseDTO<T> {
    private int status;
    private String message;
    private T data;
}