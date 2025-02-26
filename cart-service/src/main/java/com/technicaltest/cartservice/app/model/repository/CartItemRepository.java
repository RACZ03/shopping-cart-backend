package com.technicaltest.cartservice.app.model.repository;

import com.technicaltest.cartservice.app.model.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
    List<CartItemEntity> findByUserId(Long userId);
    Optional<CartItemEntity> findByUserIdAndProductId(Long userId, Long productId);

    @Modifying
    @Transactional
    @Query("DELETE FROM CartItemEntity c WHERE c.userId = :userId")
    void deleteByUserId(Long userId);
}
