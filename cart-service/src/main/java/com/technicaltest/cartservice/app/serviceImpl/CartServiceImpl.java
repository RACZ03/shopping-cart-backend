package com.technicaltest.cartservice.app.serviceImpl;

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
    public List<CartItemEntity> getCartByUser(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }

    @Override
    public CartItemEntity addToCart(Long userId, String token, CartItemEntity cartItem) {
        cartItem.setUserId(userId);

        ApiResponse productResponse = webClientBuilder.build()
                .get()
                .uri(productServiceUrl + cartItem.getProductId())
                .headers(headers -> headers.setBearerAuth(token))
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .timeout(Duration.ofSeconds(5))
                .block();

        if (productResponse == null || productResponse.getData() == null) {
            throw new ResourceNotFoundException("Product with ID " + cartItem.getProductId() + " not found.");
        }

        cartItem.setPrice(productResponse.getData().getPrice() * cartItem.getQuantity());

        return cartItemRepository.save(cartItem);
    }

    @Override
    public void removeFromCart(Long itemId) {
        if (!cartItemRepository.existsById(itemId)) {
            throw new ResourceNotFoundException("CartItem con ID " + itemId + " no encontrado.");
        }
        cartItemRepository.deleteById(itemId);
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
