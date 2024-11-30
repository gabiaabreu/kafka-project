package com.storeservice.domain.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class SortAndPageRequest {

    private String sortDirection;

    private String sortAttribute;

    @PositiveOrZero
    private int pageNumber;

    @Positive
    private int pageSize;
}
