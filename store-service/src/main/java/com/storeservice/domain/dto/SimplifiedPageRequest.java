package com.storeservice.domain.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SimplifiedPageRequest {

    @PositiveOrZero
    private Integer pageNumber = 0;

    @Positive
    private Integer pageSize = 5;
}
