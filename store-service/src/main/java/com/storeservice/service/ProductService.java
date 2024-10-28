package com.storeservice.service;

import com.storeservice.domain.dto.Product;
import com.storeservice.domain.dto.ProductCreateRequest;
import com.storeservice.domain.entity.ProductEntity;

import java.util.List;

public interface ProductService {
    ProductEntity findById(Long productId);
    // todo: nao deveria retornar Product?

    List<Product> findAll();

    boolean existsByName(String productName);

    Product save(ProductCreateRequest request);

    void updateStock(Long productId, Integer stockQty);
}
