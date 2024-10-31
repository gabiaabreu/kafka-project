package com.storeservice.controller;

import com.storeservice.domain.dto.Product;
import com.storeservice.domain.dto.ProductCreateRequest;
import com.storeservice.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductCreateRequest request) {
            var product = service.save(request);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(product.getId())
                    .toUri();

            return ResponseEntity.created(location).body(product);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        // todo: nao deveria retornar Product?
        var product = service.findById(id);

            return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        var products = service.findAll();

        return ResponseEntity.ok().body(products);
    }

    @PatchMapping("/{id}/stockUpdate")
    public ResponseEntity<String> updateStock(@PathVariable Long id, @Valid @PositiveOrZero Integer stockQty) {
            service.updateStock(id, stockQty);

            return ResponseEntity.status(HttpStatus.OK).body(
                    String.format("Stock quantity updated for product id %d = %d", id, stockQty));
    }
}
