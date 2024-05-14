package com.brian.transaction_importer_spring.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
// Basic POJO, is not persisted
public class MailMessage {
    private String from;
    private String to;
    private String subject;
    private String body;
    private String html;
    private String messageId;
    private String label;
    private Map<String, String> headers;
}
