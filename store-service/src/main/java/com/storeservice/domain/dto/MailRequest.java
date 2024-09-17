package com.storeservice.domain.dto;

import lombok.Data;

@Data
public class MailRequest {

    private String subject;

    private String message;

    private String from;

    private String to;
}
