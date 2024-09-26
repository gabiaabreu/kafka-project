package com.storeservice.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductCreateRequest {
    private String name;

    private String description;

    private BigDecimal price;

    private Integer stockQty;
}
