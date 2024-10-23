package com.storeservice.mapper;

import com.storeservice.domain.dto.Order;
import com.storeservice.domain.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {
    Order toOrder(OrderEntity entity);
}
