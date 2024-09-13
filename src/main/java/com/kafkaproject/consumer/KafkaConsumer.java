package com.kafkaproject.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    private final String TOPIC_NAME= "quickstart-events";

    @KafkaListener(topics = TOPIC_NAME, groupId = "1406")
    public void consumeMessage(String message) {
        System.out.println("Received message from topic " + TOPIC_NAME + ": " + message);
    }
}
