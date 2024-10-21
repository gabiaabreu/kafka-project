package com.storeservice.domain.dto;

import lombok.Data;

@Data
public class OrderProductRequest {
    private Long productId;

    private Integer quantity;
}
