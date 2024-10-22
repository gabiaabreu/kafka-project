package com.storeservice.service;

import com.storeservice.domain.dto.Order;
import com.storeservice.domain.dto.OrderCreateRequest;

public interface OrderService {
    Order placeOrder(final OrderCreateRequest request);
}
