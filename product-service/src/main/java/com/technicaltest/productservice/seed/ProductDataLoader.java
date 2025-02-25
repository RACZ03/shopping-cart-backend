package com.technicaltest.productservice.seed;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.technicaltest.productservice.app.dto.ProductRequestDTO;
import com.technicaltest.productservice.app.model.entity.ProductEntity;
import com.technicaltest.productservice.app.model.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

@Component
public class ProductDataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;

    public ProductDataLoader(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        if (productRepository.count() == 0) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                InputStream is = getClass().getResourceAsStream("/products.json");
                List<ProductRequestDTO> products = objectMapper.readValue(is, new TypeReference<List<ProductRequestDTO>>() {});
                for (ProductRequestDTO dto : products) {
                    ProductEntity product = ProductEntity.builder()
                            .image(dto.getImage())
                            .description(dto.getDescription())
                            .price(dto.getPrice() != null ? dto.getPrice() : new BigDecimal("0.0"))
                            .stock(dto.getStock())
                            .build();
                    productRepository.save(product);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
