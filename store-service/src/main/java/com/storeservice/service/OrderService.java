package com.storeservice.service;

import com.storeservice.domain.dto.OrderCreateRequest;
import com.storeservice.domain.dto.OrderResponse;
import com.storeservice.domain.dto.PageResponse;
import com.storeservice.domain.dto.SortAndPageRequest;

import java.time.LocalDate;

public interface OrderService {
    OrderResponse placeOrder(final OrderCreateRequest request);

    OrderResponse findById(final Long id);

    PageResponse<OrderResponse> findAll(final LocalDate minDate,
                                        final LocalDate maxDate,
                                        final SortAndPageRequest sortAndPageRequest);
}
