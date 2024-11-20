package com.storeservice.service;

import com.storeservice.domain.dto.Product;
import com.storeservice.domain.dto.ProductRequest;
import com.storeservice.domain.dto.SimplifiedPageRequest;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface ProductService {
    Product findById(Long productId);

    Page<Product> findAll(
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Integer minStock,
            String sortDirection,
            String sortAttribute,
            SimplifiedPageRequest pageRequest
    );

    boolean existsByName(String productName);

    Product save(ProductRequest request);

    Product update(Long productId, ProductRequest request);

    void updateStock(Long productId, Integer stockQty);
}
