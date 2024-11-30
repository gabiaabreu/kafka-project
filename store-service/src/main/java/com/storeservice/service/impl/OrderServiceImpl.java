package com.storeservice.service.impl;

import com.storeservice.domain.dto.*;
import com.storeservice.domain.entity.OrderEntity;
import com.storeservice.domain.entity.OrderProductEntity;
import com.storeservice.domain.entity.ProductEntity;
import com.storeservice.mapper.OrderMapper;
import com.storeservice.mapper.OrderProductMapper;
import com.storeservice.mapper.ProductMapper;
import com.storeservice.repository.OrderRepository;
import com.storeservice.service.OrderProductService;
import com.storeservice.service.OrderService;
import com.storeservice.service.ProductService;
import exception.OutOfStockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ProductService productService;

    private final OrderProductService orderProductService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderProductMapper orderProductMapper;

    @Autowired
    private ProductMapper productMapper;

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
            var productEntity = productMapper.toEntity(product);

            var updatedEntity = validateAndUpdateStock(productEntity, productRequest.getQuantity());

            var orderProduct = createOrderProductEntity(updatedEntity, productRequest.getQuantity());
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
        var order = orderRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Order with given id does not exist: " + id));

        return orderMapper.toResponse(order);
    }

    public PageResponse<OrderResponse> findAll(final LocalDate minDate,
                                               final LocalDate maxDate,
                                               final SortAndPageRequest sortAndPageRequest) {
        var sortDirection = Optional.ofNullable(sortAndPageRequest.getSortDirection())
                .filter(value -> !value.isBlank())
                .orElse("desc");
        var sortAttribute = Optional.ofNullable(sortAndPageRequest.getSortAttribute())
                .filter(value -> !value.isBlank())
                .orElse("orderDate");

        Pageable pageable = PageRequest.of(
                sortAndPageRequest.getPageNumber(),
                sortAndPageRequest.getPageSize(),
                Sort.Direction.fromString(sortDirection),
                sortAttribute
        );

        LocalDateTime minDateTime = Optional.ofNullable(minDate)
                .map(LocalDate::atStartOfDay)
                .orElse(null);

        LocalDateTime maxDateTime = Optional.ofNullable(maxDate)
                .map(date -> date.atTime(LocalTime.MAX))
                .orElse(null);

        var orders = orderRepository.findByDate(
                minDateTime,
                maxDateTime,
                pageable
        );

        var ordersPage = orders.map(order -> orderMapper.toResponse(order));
        return new PageResponse<>(ordersPage);
    }

    private BigDecimal getNetAmount(BigDecimal productTotal, BigDecimal shippingCost, Integer discount) {
        double percentage = 1 - ((double) discount / 100);
        var bigDecimalDiscount = BigDecimal.valueOf(percentage);

        return productTotal.multiply(bigDecimalDiscount).add(shippingCost);
    }

    private ProductEntity validateAndUpdateStock(ProductEntity product, Integer quantity) {
        if (product.getStockQty() < quantity) {
            throw new OutOfStockException(product.getName(), quantity, product.getStockQty());
        }

        var newQuantity = product.getStockQty() - quantity;
        productService.updateStock(product.getId(), newQuantity);

        product.setStockQty(newQuantity);
        return product;
    }

    private OrderProductEntity createOrderProductEntity(ProductEntity product, int quantity) {
        var orderProduct = new OrderProductEntity();
        orderProduct.setProduct(product);
        orderProduct.setQuantity(quantity);
        return orderProduct;
    }
}
