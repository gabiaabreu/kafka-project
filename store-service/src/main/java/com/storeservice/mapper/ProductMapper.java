package com.storeservice.mapper;

import com.storeservice.domain.dto.Product;
import com.storeservice.domain.dto.ProductRequest;
import com.storeservice.domain.entity.ProductEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {
    ProductEntity toEntity(ProductRequest request);

    ProductEntity toEntity(Product request);

    Product toProduct(ProductEntity entity);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(ProductRequest request, @MappingTarget ProductEntity entity);
}