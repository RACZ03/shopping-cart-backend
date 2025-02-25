package com.technicaltest.cartservice.app.serviceImpl;


import com.technicaltest.cartservice.app.model.entity.CartItemEntity;
import com.technicaltest.cartservice.app.model.entity.OrderEntity;
import com.technicaltest.cartservice.app.model.entity.OrderItemEntity;
import com.technicaltest.cartservice.app.model.repository.CartItemRepository;
import com.technicaltest.cartservice.app.model.repository.OrderRepository;
import com.technicaltest.cartservice.app.service.OrderService;
import com.technicaltest.cartservice.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;

    public OrderServiceImpl(OrderRepository orderRepository, CartItemRepository cartItemRepository) {
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    @Transactional
    public OrderEntity createOrder(Long userId) {
        List<CartItemEntity> cartItems = cartItemRepository.findByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new ResourceNotFoundException("There is no cart item for user " + userId);
        }

        OrderEntity order = OrderEntity.builder()
                .userId(userId)
                .orderDate(LocalDateTime.now())
                .build();

        OrderEntity finalOrder = order;
        List<OrderItemEntity> orderItems = cartItems.stream().map(cartItem ->
                OrderItemEntity.builder()
                        .order(finalOrder)
                        .productId(cartItem.getProductId())
                        .quantity(cartItem.getQuantity())
                        .price(cartItem.getPrice())
                        .build()
        ).toList();

        order.setItems(orderItems);

        order = orderRepository.save(order);

        cartItemRepository.deleteByUserId(userId);

        return order;
    }


    @Override
    public List<OrderEntity> getOrdersByUser(Long userId) {
        return orderRepository.findByUserIdOrderByOrderDateDesc(userId);
    }
}
