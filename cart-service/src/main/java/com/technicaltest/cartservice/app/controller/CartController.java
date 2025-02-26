package com.technicaltest.cartservice.app.controller;

import com.technicaltest.cartservice.app.dto.ApiResponseDTO;
import com.technicaltest.cartservice.app.dto.CartItemDTO;
import com.technicaltest.cartservice.app.model.entity.CartItemEntity;
import com.technicaltest.cartservice.app.service.CartItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartItemService cartService;

    public CartController(CartItemService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<CartItemDTO>>> getCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(authentication.getName());
        String token = authentication.getCredentials().toString();
        List<CartItemDTO> cartItems = cartService.getCartByUser(userId, token);

        return ResponseEntity.ok(
                new ApiResponseDTO<>(HttpStatus.OK.value(), "Cart retrieved successfully", cartItems)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<CartItemEntity>> addToCart(@RequestBody CartItemEntity cartItem) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(authentication.getName());
        String token = authentication.getCredentials().toString();
        CartItemEntity savedItem = cartService.addToCart(userId, token, cartItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponseDTO<>(HttpStatus.CREATED.value(), "Product added to cart", savedItem)
        );
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<ApiResponseDTO<Void>> removeFromCart(@PathVariable Long itemId) {
        cartService.removeFromCart(itemId);
        return ResponseEntity.ok(
                new ApiResponseDTO<>(HttpStatus.OK.value(), "Product removed from cart", null)
        );
    }
}
