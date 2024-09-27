package com.storeservice.service.impl;

import com.storeservice.domain.dto.Product;
import com.storeservice.domain.dto.ProductCreateRequest;
import com.storeservice.domain.dto.ResponseModel;
import com.storeservice.mapper.ProductMapper;
import com.storeservice.repository.ProductRepository;
import com.storeservice.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseEntity<ResponseModel<Product>> save(ProductCreateRequest request) {
//        var exists = productRepository.existsByName(request.getName());
//
//        if (exists) {
//            return ResponseEntity.badRequest().body("Product with given name already exists");
//        }

        var entity = ProductMapper.INSTANCE.toEntity(request);
        productRepository.save(entity);

        var product = ProductMapper.INSTANCE.toProduct(entity);

        ResponseModel<Product> response = new ResponseModel<>();
        response.setData(product);
        response.setMessage("Product saved successfully.");

        return ResponseEntity.status(201).body(response);
    }
}
