package com.storeservice.service;

import com.storeservice.domain.dto.Product;
import com.storeservice.domain.dto.ProductCreateRequest;
import com.storeservice.domain.entity.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    ProductEntity findById(Long productId);

    boolean existsByName(String productName);

    Product save(ProductCreateRequest request);

    void updateStock(Long productId, Integer stockQty);
}
