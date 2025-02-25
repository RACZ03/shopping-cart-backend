package com.technicaltest.cartservice.app.controller;

import com.technicaltest.cartservice.app.dto.ApiResponseDTO;
import com.technicaltest.cartservice.app.model.entity.OrderEntity;
import com.technicaltest.cartservice.app.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<OrderEntity>> createOrder() {
        Long userId = getUserIdFromToken();
        OrderEntity order = orderService.createOrder(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponseDTO<>(HttpStatus.CREATED.value(), "Order created successfully", order)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<OrderEntity>>> getOrders() {
        Long userId = getUserIdFromToken();
        List<OrderEntity> orders = orderService.getOrdersByUser(userId);

        return ResponseEntity.ok(
                new ApiResponseDTO<>(HttpStatus.OK.value(), "Orders retrieved successfully", orders)
        );
    }

    private Long getUserIdFromToken() {
        return Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }
}
