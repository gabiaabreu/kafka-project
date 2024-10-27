package com.storeservice.service.impl;

import com.storeservice.domain.entity.OrderProductEntity;
import com.storeservice.repository.OrderProductRepository;
import com.storeservice.service.OrderProductService;
import org.springframework.stereotype.Service;

@Service
public class OrderProductServiceImpl implements OrderProductService {

    private final OrderProductRepository orderProductRepository;

    public OrderProductServiceImpl(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

    public void save(OrderProductEntity entity) {
        orderProductRepository.save(entity);
    }
}
