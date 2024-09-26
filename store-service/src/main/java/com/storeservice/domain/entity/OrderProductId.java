package com.storeservice.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class OrderProductId implements Serializable {

    private Long orderId;

    private Long productId;
}
