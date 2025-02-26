package com.technicaltest.cartservice.app.serviceImpl;

import com.technicaltest.cartservice.app.dto.CartItemDTO;
import com.technicaltest.cartservice.app.model.entity.CartItemEntity;
import com.technicaltest.cartservice.app.model.repository.CartItemRepository;
import com.technicaltest.cartservice.app.service.CartItemService;
import com.technicaltest.cartservice.exception.ResourceNotFoundException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final WebClient.Builder webClientBuilder;

    @Value("${product.service.url}")
    private String productServiceUrl;

    public CartServiceImpl(CartItemRepository cartItemRepository, WebClient.Builder webClientBuilder) {
        this.cartItemRepository = cartItemRepository;
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public List<CartItemDTO> getCartByUser(Long userId, String token) {
        List<CartItemEntity> cartItems = cartItemRepository.findByUserId(userId);

        return cartItems.stream().map(cartItem -> {
            ProductData product = fetchProductDetails(cartItem.getProductId(), token);
            return new CartItemDTO(
                    cartItem.getId(),
                    cartItem.getUserId(),
                    cartItem.getProductId(),
                    product.getDescription(),
                    product.getImage() != null ? product.getImage() : "https://via.placeholder.com/150",
                    product.getPrice(),
                    cartItem.getQuantity(),
                    product.getPrice() * cartItem.getQuantity()
            );
        }).collect(Collectors.toList());
    }


    @Override
    public CartItemEntity addToCart(Long userId, String token, CartItemEntity cartItem) {
        cartItem.setUserId(userId);

        ProductData product = fetchProductDetails(cartItem.getProductId(), token);

        if (product == null) {
            throw new ResourceNotFoundException("Product with ID " + cartItem.getProductId() + " not found.");
        }

        Optional<CartItemEntity> existingCartItem = cartItemRepository.findByUserIdAndProductId(userId, cartItem.getProductId());

        if (existingCartItem.isPresent()) {
            CartItemEntity existingItem = existingCartItem.get();
            existingItem.setQuantity(cartItem.getQuantity());
            existingItem.setPrice(product.getPrice() * existingItem.getQuantity());
            return cartItemRepository.save(existingItem);
        } else {
            cartItem.setPrice(product.getPrice() * cartItem.getQuantity());
            return cartItemRepository.save(cartItem);
        }
    }

    @Override
    public void removeFromCart(Long itemId) {
        if (!cartItemRepository.existsById(itemId)) {
            throw new ResourceNotFoundException("CartItem con ID " + itemId + " no encontrado.");
        }
        cartItemRepository.deleteById(itemId);
    }

    private ProductData fetchProductDetails(Long productId, String token) {
        ApiResponse response = webClientBuilder.build()
                .get()
                .uri(productServiceUrl + "/" + productId)
                .headers(headers -> {
                    if (token != null && !token.isEmpty()) {
                        headers.setBearerAuth(token);
                    }
                })
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .timeout(Duration.ofSeconds(5))
                .block();

        if (response == null || response.getData() == null) {
            throw new ResourceNotFoundException("Product with ID " + productId + " not found.");
        }

        return response.getData();
    }

    @Getter
    private static class ApiResponse {
        private int status;
        private String message;
        private ProductData data;
    }

    @Getter
    private static class ProductData {
        private Long id;
        private String image;
        private String description;
        private Double price;
        private Integer stock;
    }
}
