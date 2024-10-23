package com.storeservice.mapper;

import com.storeservice.domain.dto.Product;
import com.storeservice.domain.dto.ProductCreateRequest;
import com.storeservice.domain.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {
    ProductEntity toEntity(ProductCreateRequest request);

    Product toProduct(ProductEntity entity);
}