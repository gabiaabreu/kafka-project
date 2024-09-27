package com.storeservice.service.impl;

import com.storeservice.domain.dto.Product;
import com.storeservice.domain.dto.ProductCreateRequest;
import com.storeservice.mapper.ProductMapper;
import com.storeservice.repository.ProductRepository;
import com.storeservice.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(ProductCreateRequest request) {
        var exists = productRepository.existsByName(request.getName());
        if (exists) {
            throw new IllegalArgumentException("Product with given name already exists");
        }

        var entity = ProductMapper.INSTANCE.toEntity(request);
        var savedEntity = productRepository.save(entity);

        return ProductMapper.INSTANCE.toProduct(savedEntity);
    }
}
