package com.storeservice.service.impl;

import com.storeservice.domain.dto.OrderCreateRequest;
import com.storeservice.domain.dto.OrderProductRequest;
import com.storeservice.domain.dto.OrderResponse;
import com.storeservice.domain.entity.OrderEntity;
import com.storeservice.domain.entity.OrderProductEntity;
import com.storeservice.domain.entity.ProductEntity;
import com.storeservice.mapper.OrderMapper;
import com.storeservice.mapper.OrderProductMapper;
import com.storeservice.repository.OrderRepository;
import com.storeservice.service.OrderProductService;
import com.storeservice.service.OrderService;
import com.storeservice.service.ProductService;
import exception.OutOfStockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ProductService productService;

    private final OrderProductService orderProductService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderProductMapper orderProductMapper;

    public OrderServiceImpl(final OrderRepository orderRepository,
                            final ProductService productService,
                            final OrderProductService orderProductService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.orderProductService = orderProductService;
    }

    public OrderResponse placeOrder(final OrderCreateRequest request) {
        var order = new OrderEntity();
        order.setCustomerId(request.getCustomerId());
        order.setOrderDate(LocalDateTime.now());
        order.setPaymentMethod(request.getPaymentMethod());
        order.setDiscount(request.getDiscount());
        order.setShippingCost(request.getShippingCost()); // todo: fazer sets com mapper?

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
            orderProductService.save(orderProduct);
        }

        var orderProductResponseList = orderProductMapper.toOrderProductResponse(orderProductEntities);
        var orderResponse = orderMapper.toResponse(order);
        orderResponse.setOrderProducts(orderProductResponseList);

        return orderResponse;
    }

    public OrderResponse findById(final Long id) {
        var orderOptional = orderRepository.findById(id);
        // todo: orderOptional.get.orElse(throw....
        if (orderOptional.isEmpty()) {
            throw new NotFoundException("Order with given id does not exist: " + id);
        }

        return orderMapper.toResponse(orderOptional.get());
    }

    public List<OrderResponse> findAll() {
        var orders = orderRepository.findAll();
        return orders.stream().map(order -> orderMapper.toResponse(order)).toList();
    }

    private BigDecimal getNetAmount(BigDecimal productTotal, BigDecimal shippingCost, Integer discount) {
        double percentage = 1 - ((double) discount / 100);
        var bigDecimalDiscount = BigDecimal.valueOf(percentage);

        return productTotal.multiply(bigDecimalDiscount).add(shippingCost);
    }

    private void validateAndUpdateStock(ProductEntity product, Integer quantity) {
        if (product.getStockQty() < quantity) {
            throw new OutOfStockException(product.getName(), quantity, product.getStockQty());
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
