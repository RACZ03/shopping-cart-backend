package com.technicaltest.productservice.app.model.repository;

import com.technicaltest.productservice.app.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
