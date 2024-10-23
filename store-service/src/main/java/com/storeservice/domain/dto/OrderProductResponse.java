package com.storeservice.domain.dto;

import lombok.Data;

@Data
public class OrderProductResponse {
    private Long id;

    private Integer quantity;

    private Product product;
}
