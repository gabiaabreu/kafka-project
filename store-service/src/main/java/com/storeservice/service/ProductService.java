package com.storeservice.service;

import com.storeservice.domain.dto.Product;
import com.storeservice.domain.dto.ProductRequest;

import java.util.List;

public interface ProductService {
    Product findById(Long productId);

    List<Product> findAll();

    boolean existsByName(String productName);

    Product save(ProductRequest request);

    Product update(Long productId, ProductRequest request);

    void updateStock(Long productId, Integer stockQty);
}
