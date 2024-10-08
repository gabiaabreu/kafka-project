package com.storeservice.service;

import com.storeservice.domain.dto.MailRequest;
import com.storeservice.domain.entity.Mail;
import com.storeservice.producer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MailService {

    private final KafkaProducer kafkaProducer;

    public void sendMail(MailRequest mailRequest) {

        var mail = Mail.builder().id(1L).to(mailRequest.getTo()).from(mailRequest.getFrom()).message(mailRequest.getMessage())
                .status("loading").subject(mailRequest.getSubject()).timestamp(LocalDateTime.now()).build();

        kafkaProducer.sendMessage(mail);
    }
}
