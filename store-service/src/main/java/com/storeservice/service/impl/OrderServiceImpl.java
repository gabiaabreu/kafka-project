package com.storeservice.service.impl;

import com.storeservice.domain.dto.OrderCreateRequest;
import com.storeservice.domain.dto.OrderCreateResponse;
import com.storeservice.domain.dto.OrderProductRequest;
import com.storeservice.domain.dto.OrderProductResponse;
import com.storeservice.domain.entity.OrderEntity;
import com.storeservice.domain.entity.OrderProductEntity;
import com.storeservice.mapper.OrderProductMapper;
import com.storeservice.repository.OrderProductRepository;
import com.storeservice.repository.OrderRepository;
import com.storeservice.repository.ProductRepository;
import com.storeservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final OrderProductRepository orderProductRepository;

    @Autowired
    private OrderProductMapper orderProductMapper;

    public OrderServiceImpl(final OrderRepository orderRepository,
                            final ProductRepository productRepository,
                            final OrderProductRepository orderProductRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
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
            var product = productRepository.findById(productRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException(
                            "Product not found with id: " + productRequest.getProductId()));

            if (product.getStockQty() < productRequest.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product " + product.getName());
            }

            product.setStockQty(product.getStockQty() - productRequest.getQuantity());

            var orderProduct = new OrderProductEntity();
            orderProduct.setProduct(product);
            orderProduct.setQuantity(productRequest.getQuantity());

            productTotal = productTotal.add(
                    product.getPrice().multiply(BigDecimal.valueOf(productRequest.getQuantity())));
            orderProductEntities.add(orderProduct);
        }

        // todo: rever / separar metodo
        double discount = 1 - ((double) request.getDiscount() / 100);
        var bigDecimalDiscount = BigDecimal.valueOf(discount);
        var discountedTotal = productTotal.multiply(bigDecimalDiscount);
        order.setTotalAmount(discountedTotal.add(request.getShippingCost()));

        order = orderRepository.save(order);

        for (OrderProductEntity orderProduct : orderProductEntities) {
            orderProduct.setOrder(order);
            orderProductRepository.save(orderProduct);
        }

        List<OrderProductResponse> productResponseList = orderProductMapper
                .toOrderProductResponse(orderProductEntities);

        return OrderCreateResponse.builder()
                .id(order.getId())
                .customerId(order.getCustomerId())
                .orderDate(order.getOrderDate())
                .totalAmount(order.getTotalAmount())
                .shippingCost(request.getShippingCost())
                .discount(request.getDiscount())
                .paymentMethod(order.getPaymentMethod())
                .products(productResponseList)
                .build();
    }
}
