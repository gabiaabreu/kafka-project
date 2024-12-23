package com.storeservice.controller;

import com.storeservice.domain.dto.OrderCreateRequest;
import com.storeservice.domain.dto.OrderResponse;
import com.storeservice.domain.dto.PageResponse;
import com.storeservice.domain.dto.SortAndPageRequest;
import com.storeservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
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
    public ResponseEntity<PageResponse<OrderResponse>> getOrders(
            @RequestParam(required = false) LocalDate minDate,
            @RequestParam(required = false) LocalDate maxDate,
            @Valid SortAndPageRequest sortAndPageRequest
    ) {
        var orders = service.findAll(minDate, maxDate, sortAndPageRequest);

        return ResponseEntity.ok().body(orders);
    }
}
