package com.kafkaproject.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kafkaproject.domain.entity.Mail;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    private final String TOPIC_NAME= "quickstart-events";

    ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = TOPIC_NAME, groupId = "1406")
    public void consumeMessage(String message) throws JsonProcessingException {
        objectMapper.registerModule(new JavaTimeModule());

        Mail mail = objectMapper.readValue(message, Mail.class);
        System.out.println("Received message from topic " + TOPIC_NAME + ": " + mail);
    }
}
