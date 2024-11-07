package com.storeservice.service;

import com.storeservice.domain.dto.Product;
import com.storeservice.domain.dto.ProductRequest;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    Product findById(Long productId);

    List<Product> findAll(
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Integer minStock,
            String sortDirection,
            String sortAttribute
    );

    boolean existsByName(String productName);

    Product save(ProductRequest request);

    Product update(Long productId, ProductRequest request);

    void updateStock(Long productId, Integer stockQty);
}
