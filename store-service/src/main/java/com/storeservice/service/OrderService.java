package com.storeservice.service;

import com.storeservice.domain.dto.OrderCreateRequest;
import com.storeservice.domain.dto.OrderCreateResponse;

public interface OrderService {
    OrderCreateResponse placeOrder(final OrderCreateRequest request);
}
