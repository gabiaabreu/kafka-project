package com.storeservice.service;


import com.storeservice.domain.dto.PageResponse;
import com.storeservice.domain.dto.Product;
import com.storeservice.domain.dto.ProductRequest;
import com.storeservice.domain.dto.SortAndPageRequest;

import java.math.BigDecimal;

public interface ProductService {
    Product findById(Long productId);

    PageResponse<Product> findAll(BigDecimal minPrice,
                                  BigDecimal maxPrice,
                                  Integer minStock,
                                  SortAndPageRequest sortAndPageRequest);

    boolean existsByName(String productName);

    Product save(ProductRequest request);

    Product update(Long productId, ProductRequest request);

    void updateStock(Long productId, Integer stockQty);
}
