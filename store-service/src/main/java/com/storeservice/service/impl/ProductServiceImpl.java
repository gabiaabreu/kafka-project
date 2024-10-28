package com.storeservice.service.impl;

import com.storeservice.domain.dto.Product;
import com.storeservice.domain.dto.ProductCreateRequest;
import com.storeservice.domain.entity.ProductEntity;
import com.storeservice.mapper.ProductMapper;
import com.storeservice.repository.ProductRepository;
import com.storeservice.service.ProductService;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository,
                              ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductEntity findById(Long productId) {
        var productOptional = productRepository.findById(productId);

        if (productOptional.isEmpty()) {
            throw new NotFoundException("Product with given id does not exist: " + productId);
        }
        return productOptional.get();
    }

    @Override
    public List<Product> findAll() {
        var products = productRepository.findAll();

        return products.stream().map(productMapper::toProduct).toList();
    }

    @Override
    public boolean existsByName(String productName) {
        return productRepository.existsByName(productName);
    }

    @Override
    public Product save(ProductCreateRequest request) {
        var exists = existsByName(request.getName());
        if (exists) {
            throw new IllegalArgumentException("Product with given name already exists: " + request.getName());
        }

        var entity = productMapper.toEntity(request);
        var savedEntity = productRepository.save(entity);

        return productMapper.toProduct(savedEntity);
    }

    @Override
    public void updateStock(Long productId, Integer stockQty) {
        var product = findById(productId);

        product.setStockQty(stockQty);
        productRepository.save(product);
    }

}
