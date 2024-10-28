package com.storeservice.service;

import com.storeservice.domain.dto.OrderCreateRequest;
import com.storeservice.domain.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse placeOrder(final OrderCreateRequest request);

    OrderResponse findById(final Long id);

    List<OrderResponse> findAll();
}
