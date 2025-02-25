package com.technicaltest.cartservice.app.serviceImpl;

import com.technicaltest.cartservice.app.model.entity.CartItemEntity;
import com.technicaltest.cartservice.app.model.repository.CartItemRepository;
import com.technicaltest.cartservice.app.service.CartItemService;
import com.technicaltest.cartservice.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;

    public CartServiceImpl(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public List<CartItemEntity> getCartByUser(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }

    @Override
    public CartItemEntity addToCart(CartItemEntity cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public void removeFromCart(Long itemId) {
        if (!cartItemRepository.existsById(itemId)) {
            throw new ResourceNotFoundException("CartItem con ID " + itemId + " no encontrado.");
        }
        cartItemRepository.deleteById(itemId);
    }
}
