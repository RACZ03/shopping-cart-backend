package com.technicaltest.cartservice.app.model.repository;

import com.technicaltest.cartservice.app.model.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByUserIdOrderByOrderDateDesc(Long userId);
}