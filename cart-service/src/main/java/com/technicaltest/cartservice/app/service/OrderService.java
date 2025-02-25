package com.technicaltest.cartservice.app.service;

import com.technicaltest.cartservice.app.model.entity.OrderEntity;

import java.util.List;

public interface OrderService {
    OrderEntity createOrder(Long userId);
    List<OrderEntity> getOrdersByUser(Long userId);
}