package com.storeservice.service;

import com.storeservice.domain.dto.Product;
import com.storeservice.domain.dto.ProductFilterRequest;
import com.storeservice.domain.dto.ProductRequest;
import org.springframework.data.domain.Page;

public interface ProductService {
    Product findById(Long productId);

    Page<Product> findAll(ProductFilterRequest request);

    boolean existsByName(String productName);

    Product save(ProductRequest request);

    Product update(Long productId, ProductRequest request);

    void updateStock(Long productId, Integer stockQty);
}
