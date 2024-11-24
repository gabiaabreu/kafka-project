package com.storeservice.domain.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductFilterRequest {

    @Positive
    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    @Positive
    private Integer minStock;

    private String sortDirection;

    private String sortAttribute;

    @PositiveOrZero
    private int pageNumber;

    @Positive
    private int pageSize;
}
