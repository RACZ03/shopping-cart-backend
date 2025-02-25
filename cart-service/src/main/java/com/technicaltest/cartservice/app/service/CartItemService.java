package com.technicaltest.cartservice.app.service;

import com.technicaltest.cartservice.app.model.entity.CartItemEntity;

import java.util.List;

public interface CartService {
    List<CartItemEntity> getCartByUser(Long userId);
    CartItemEntity addToCart(CartItemEntity cartItem);
    void removeFromCart(Long itemId);
}