package com.technicaltest.cartservice.app.service;

import com.technicaltest.cartservice.app.dto.CartItemDTO;
import com.technicaltest.cartservice.app.model.entity.CartItemEntity;

import java.util.List;

public interface CartItemService {
    List<CartItemDTO> getCartByUser(Long userId, String token);
    CartItemEntity addToCart(Long userId, String token, CartItemEntity cartItem);
    void removeFromCart(Long itemId);
}