package com.technicaltest.cartservice.app.service;

import com.technicaltest.cartservice.app.model.entity.CartItemEntity;

import java.util.List;

public interface CartItemService {
    List<CartItemEntity> getCartByUser(Long userId);
    CartItemEntity addToCart(Long userId, String token, CartItemEntity cartItem);
    void removeFromCart(Long itemId);
}