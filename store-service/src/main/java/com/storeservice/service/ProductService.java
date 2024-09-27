package com.storeservice.service;

import com.storeservice.domain.dto.Product;
import com.storeservice.domain.dto.ProductCreateRequest;

public interface ProductService {
    Product save(ProductCreateRequest request);
}
