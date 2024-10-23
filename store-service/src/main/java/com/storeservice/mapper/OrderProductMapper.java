package com.storeservice.mapper;

import com.storeservice.domain.dto.OrderProduct;
import com.storeservice.domain.entity.OrderProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderProductMapper {

    OrderProduct from(OrderProductEntity entity);

    List<OrderProduct> from(List<OrderProductEntity> entityList);
}
