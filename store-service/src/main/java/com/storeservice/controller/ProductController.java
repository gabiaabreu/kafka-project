package com.storeservice.controller;

import com.storeservice.domain.dto.Product;
import com.storeservice.domain.dto.ProductCreateRequest;
import com.storeservice.domain.dto.ResponseModel;
import com.storeservice.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl service;

    @PostMapping("/create")
    public ResponseEntity<ResponseModel<Product>> createProduct(@RequestBody ProductCreateRequest request) {
        try {
            return service.save(request);
        } catch (Exception e) {
            ResponseModel<Product> response = new ResponseModel<>();
            response.setErrorMessage(e.getMessage());

            return ResponseEntity.status(400).body(response);
        }
    }
}
