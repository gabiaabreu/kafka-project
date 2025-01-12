package com.paymentservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;

    private Long customerId;

    private LocalDateTime orderDate;

    private BigDecimal totalAmount;

    private BigDecimal shippingCost;

    private Integer discount;

    private String paymentMethod;

    private String paymentStatus = "PENDING";

    private List<OrderProduct> orderProducts;
}
