package com.storeservice.controller;

import com.storeservice.domain.dto.MailRequest;
import com.storeservice.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MailService mailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessageToKafka(@RequestBody MailRequest mailRequest) {
        try {
            mailService.sendMail(mailRequest);
            return ResponseEntity.ok("Mail sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send mail: " + e.getMessage());
        }
    }
}