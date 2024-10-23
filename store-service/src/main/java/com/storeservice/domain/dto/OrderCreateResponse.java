package com.storeservice.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class OrderCreateResponse {
    private Long id;

    private Long customerId;

    private LocalDateTime orderDate;

    private BigDecimal totalAmount;

    private BigDecimal shippingCost;

    private Integer discount;

    private String paymentMethod;

    private List<OrderProductResponse> products;
}
