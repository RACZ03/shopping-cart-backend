package com.technicaltest.cartservice.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartItemDTO {
    private Long id;
    private Long userId;
    private Long productId;
    private String productName;
    private String productImage;
    private Double productPrice;
    private Integer quantity;
    private Double totalPrice;
}
