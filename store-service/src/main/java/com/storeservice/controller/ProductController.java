package com.storeservice.controller;

import com.storeservice.domain.dto.ProductCreateRequest;
import com.storeservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductCreateRequest request) {
        try {
            var product = service.save(request);

            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
