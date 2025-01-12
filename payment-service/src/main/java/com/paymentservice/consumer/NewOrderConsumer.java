package com.paymentservice.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.paymentservice.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NewOrderConsumer {

    private final static String TOPIC_NAME = "order-placed";

    ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = TOPIC_NAME, groupId = "1406")
    public void consume(String message) throws JsonProcessingException {
        objectMapper.registerModule(new JavaTimeModule());

        Order order = objectMapper.readValue(message, Order.class);
        log.info("Received new order from topic " + TOPIC_NAME + ": {}", order);
    }
}
