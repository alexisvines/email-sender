package com.procanchas.email.model;

import lombok.Data;

import java.util.Map;

/**
 * POJO que contiene los datos de un mail
 */
@Data
public class Mail {
    private String from;
    private String to;
    private String subject;
    private String content;
    private String template;
    private User user;
    private Map< String, Object > model;
}
