package com.kafkaproject.producer;

import com.kafkaproject.domain.entity.Mail;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, Mail> kafkaTemplate;
    private final String TOPIC_NAME= "quickstart-events";

    public void sendMessage(Mail message) {
        kafkaTemplate.send(TOPIC_NAME, message);
        System.out.println("Message sent to topic " + TOPIC_NAME + ": " + message);
    }
}