package com.storeservice.controller;

import com.storeservice.domain.dto.PageResponse;
import com.storeservice.domain.dto.Product;
import com.storeservice.domain.dto.ProductRequest;
import com.storeservice.domain.dto.SortAndPageRequest;
import com.storeservice.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest request) {
        var product = service.save(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getId())
                .toUri();

        return ResponseEntity.created(location).body(product);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        var product = service.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @GetMapping
    public ResponseEntity<PageResponse<Product>> getAllProducts(
            @RequestParam(required = false) @PositiveOrZero BigDecimal minPrice,
            @RequestParam(required = false) @Positive BigDecimal maxPrice,
            @RequestParam(required = false) @PositiveOrZero Integer minStock,
            @Valid SortAndPageRequest sortAndPageRequest) {
        var products = service.findAll(minPrice, maxPrice, minStock, sortAndPageRequest);

        return ResponseEntity.ok().body(products);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,
                                                 @RequestBody @Valid ProductRequest request) {
        var product = service.update(id, request);

        return ResponseEntity.ok().body(product);
    }

    @PatchMapping("/{id}/stockUpdate")
    public ResponseEntity<String> updateStock(@PathVariable Long id, @Valid @PositiveOrZero Integer stockQty) {
        service.updateStock(id, stockQty);

        return ResponseEntity.status(HttpStatus.OK).body(
                String.format("Stock quantity updated for product id %d = %d", id, stockQty));
    }
}
