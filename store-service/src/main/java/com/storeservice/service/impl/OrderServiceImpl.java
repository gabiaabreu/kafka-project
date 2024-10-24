package com.storeservice.service.impl;

import com.storeservice.domain.dto.OrderCreateRequest;
import com.storeservice.domain.dto.OrderCreateResponse;
import com.storeservice.domain.dto.OrderProductRequest;
import com.storeservice.domain.entity.OrderEntity;
import com.storeservice.domain.entity.OrderProductEntity;
import com.storeservice.domain.entity.ProductEntity;
import com.storeservice.mapper.OrderProductMapper;
import com.storeservice.repository.OrderProductRepository;
import com.storeservice.repository.OrderRepository;
import com.storeservice.service.OrderService;
import com.storeservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ProductService productService;

    //todo: criar metodos service
    private final OrderProductRepository orderProductRepository;

    @Autowired
    private OrderProductMapper orderProductMapper;

    public OrderServiceImpl(final OrderRepository orderRepository,
                            final ProductService productService,
                            final OrderProductRepository orderProductRepository) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.orderProductRepository = orderProductRepository;
    }

    public OrderCreateResponse placeOrder(final OrderCreateRequest request) {
        var order = new OrderEntity();
        order.setCustomerId(request.getCustomerId());
        order.setOrderDate(LocalDateTime.now());
        order.setPaymentMethod(request.getPaymentMethod());

        var productTotal = BigDecimal.ZERO;
        List<OrderProductEntity> orderProductEntities = new ArrayList<>();

        for (OrderProductRequest productRequest : request.getProducts()) {
            var product = productService.findById(productRequest.getProductId());

            validateAndUpdateStock(product, productRequest.getQuantity());

            var orderProduct = createOrderProductEntity(product, productRequest.getQuantity());
            orderProductEntities.add(orderProduct);

            productTotal = productTotal.add(
                    product.getPrice()
                            .multiply(BigDecimal.valueOf(productRequest.getQuantity()))
            );
        }

        order.setTotalAmount(getNetAmount(productTotal, request.getShippingCost(), request.getDiscount()));
        order = orderRepository.save(order);

        for (OrderProductEntity orderProduct : orderProductEntities) {
            orderProduct.setOrder(order);
            orderProductRepository.save(orderProduct);
        }

        return OrderCreateResponse.builder()
                .id(order.getId())
                .customerId(order.getCustomerId())
                .orderDate(order.getOrderDate())
                .totalAmount(order.getTotalAmount())
                .shippingCost(request.getShippingCost())
                .discount(request.getDiscount())
                .paymentMethod(order.getPaymentMethod())
                .products(orderProductMapper
                        .toOrderProductResponse(orderProductEntities))
                .build();
    }

    private BigDecimal getNetAmount(BigDecimal productTotal, BigDecimal shippingCost, Integer discount) {
        double percentage = 1 - ((double) discount / 100);
        var bigDecimalDiscount = BigDecimal.valueOf(percentage);

        return productTotal.multiply(bigDecimalDiscount).add(shippingCost);
    }

    private void validateAndUpdateStock(ProductEntity product, Integer quantity) {
        if (product.getStockQty() < quantity) {
            throw new RuntimeException("Insufficient stock for product " + product.getName());
        }

        productService.updateStock(product.getId(),
                product.getStockQty() - quantity);
    }

    private OrderProductEntity createOrderProductEntity(ProductEntity product, int quantity) {
        var orderProduct = new OrderProductEntity();
        orderProduct.setProduct(product);
        orderProduct.setQuantity(quantity);
        return orderProduct;
    }}
