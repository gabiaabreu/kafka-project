package com.storeservice.domain.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderCreateRequest {
    @NotNull
    private Long customerId;

    @NotNull
    private List<OrderProductRequest> products;

    private BigDecimal shippingCost;

    @Positive
    @Max(value = 100)
    private Integer discount;

    @NotBlank
    private String paymentMethod;
}
