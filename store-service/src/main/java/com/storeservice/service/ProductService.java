package com.storeservice.service;

import com.storeservice.domain.dto.PageResponse;
import com.storeservice.domain.dto.Product;
import com.storeservice.domain.dto.ProductFilterRequest;
import com.storeservice.domain.dto.ProductRequest;

public interface ProductService {
    Product findById(Long productId);

    PageResponse<Product> findAll(ProductFilterRequest request);

    boolean existsByName(String productName);

    Product save(ProductRequest request);

    Product update(Long productId, ProductRequest request);

    void updateStock(Long productId, Integer stockQty);
}
