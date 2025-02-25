package com.technicaltest.productservice.app.controller;

import com.technicaltest.productservice.app.dto.ApiResponse;
import com.technicaltest.productservice.app.dto.ProductRequestDTO;
import com.technicaltest.productservice.app.dto.ProductResponseDTO;
import com.technicaltest.productservice.app.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getAllProducts() {
        List<ProductResponseDTO> products = productService.getAllProducts();
        ApiResponse<List<ProductResponseDTO>> response = new ApiResponse<>(HttpStatus.OK.value(), "Products fetched successfully", products);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDTO>> createProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
        ProductResponseDTO product = productService.createProduct(productRequestDTO);
        ApiResponse<ProductResponseDTO> response = new ApiResponse<>(HttpStatus.CREATED.value(), "Product created successfully", product);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO productRequestDTO) {
        ProductResponseDTO product = productService.updateProduct(id, productRequestDTO);
        ApiResponse<ProductResponseDTO> response = new ApiResponse<>(HttpStatus.OK.value(), "Product updated successfully", product);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        ApiResponse<Void> response = new ApiResponse<>(HttpStatus.OK.value(), "Product deleted successfully", null);
        return ResponseEntity.ok(response);
    }
}
