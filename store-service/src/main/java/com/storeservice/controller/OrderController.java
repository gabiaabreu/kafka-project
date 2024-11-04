package com.storeservice.controller;

import com.storeservice.domain.dto.OrderCreateRequest;
import com.storeservice.domain.dto.OrderResponse;
import com.storeservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderCreateRequest request) {
        var order = service.placeOrder(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(order.getId())
                .toUri();

        return ResponseEntity.created(location).body(order);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
        var order = service.findById(id);

        return ResponseEntity.ok().body(order);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrders() {
        var orders = service.findAll();

        return ResponseEntity.ok().body(orders);
    }
}
