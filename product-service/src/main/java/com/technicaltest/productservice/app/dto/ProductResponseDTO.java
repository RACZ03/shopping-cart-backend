package com.technicaltest.productservice.app.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponseDTO {
    private Long id;
    private String image;
    private String description;
    private BigDecimal price;
    private Integer stock;
}
