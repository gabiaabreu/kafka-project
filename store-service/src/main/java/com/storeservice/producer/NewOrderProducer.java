package com.storeservice.producer;

import com.storeservice.domain.dto.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NewOrderProducer {

    private final KafkaTemplate<String, Order> kafkaTemplate;
    private static final String TOPIC_NAME = "order-placed";

    public void send(Order message) {
        kafkaTemplate.send(TOPIC_NAME, message);
        log.info("New order placed and sent to topic " + TOPIC_NAME + ": {}", message);
    }
}