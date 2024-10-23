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

    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository,
                              ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public Product save(ProductCreateRequest request) {
        var exists = productRepository.existsByName(request.getName());
        if (exists) {
            throw new IllegalArgumentException("Product with given name already exists");
        }

        var entity = productMapper.toEntity(request);
        var savedEntity = productRepository.save(entity);

        return productMapper.toProduct(savedEntity);
    }
}
