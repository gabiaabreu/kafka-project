package com.kafkaproject.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Mail {

    private Long id;

    private String subject;

    private String message;

    private String from;

    private String to;

    private LocalDateTime timestamp;

    private String status;
}
