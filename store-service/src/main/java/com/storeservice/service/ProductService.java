package com.storeservice.service;

import com.storeservice.domain.dto.Product;
import com.storeservice.domain.dto.ProductCreateRequest;
import com.storeservice.domain.dto.ResponseModel;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    ResponseEntity<ResponseModel<Product>> save(ProductCreateRequest request);
}
