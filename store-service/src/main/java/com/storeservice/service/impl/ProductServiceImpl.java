package com.storeservice.service.impl;

import com.storeservice.domain.dto.PageResponse;
import com.storeservice.domain.dto.Product;
import com.storeservice.domain.dto.ProductRequest;
import com.storeservice.domain.dto.SortAndPageRequest;
import com.storeservice.mapper.ProductMapper;
import com.storeservice.repository.ProductRepository;
import com.storeservice.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.math.BigDecimal;
import java.util.Optional;

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
    public Product findById(Long productId) {
        var product = productRepository.findById(productId).orElseThrow(
                () -> new NotFoundException("Product with given id does not exist: " + productId)
        );

        return productMapper.toProduct(product);
    }

    @Override
    public PageResponse<Product> findAll(BigDecimal minPrice,
                                         BigDecimal maxPrice,
                                         Integer minStock,
                                         SortAndPageRequest sortAndPageRequest) {

        var sortDirection = Optional.ofNullable(sortAndPageRequest.getSortDirection())
                .filter(value -> !value.isBlank())
                .orElse("asc");
        var sortAttribute = Optional.ofNullable(sortAndPageRequest.getSortAttribute())
                .filter(value -> !value.isBlank())
                .orElse("name");

        Pageable pageable = PageRequest.of(
                sortAndPageRequest.getPageNumber(),
                sortAndPageRequest.getPageSize(),
                Sort.Direction.fromString(sortDirection),
                sortAttribute
        );

        var entityPage = productRepository.findByPriceAndStock(
                minPrice,
                maxPrice,
                minStock,
                pageable
        );

        var productsPage = entityPage.map(productMapper::toProduct);
        return new PageResponse<>(productsPage);
    }

    @Override
    public boolean existsByName(String productName) {
        return productRepository.existsByName(productName);
    }

    @Override
    public Product save(ProductRequest request) {
        var exists = existsByName(request.getName());
        if (exists) {
            throw new IllegalArgumentException("Product with given name already exists: " + request.getName());
        }

        var entity = productMapper.toEntity(request);
        var savedEntity = productRepository.save(entity);

        return productMapper.toProduct(savedEntity);
    }

    @Override
    public Product update(Long productId, ProductRequest request) {
        var existingProduct = findById(productId);
        var entity = productMapper.toEntity(existingProduct);

        productMapper.updateEntity(request, entity);

        var updatedProduct = productRepository.save(entity);
        return productMapper.toProduct(updatedProduct);
    }

    @Override
    public void updateStock(Long productId, Integer stockQty) {
        var product = findById(productId);

        product.setStockQty(stockQty);
        productRepository.save(productMapper.toEntity(product));
    }
}
