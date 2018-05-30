package com.procanchas.email.model;

import lombok.*;

import java.util.Map;

/**
 * POJO que contiene los datos de un mail
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Mail {
    @NonNull
    private String to;
    @NonNull
    private String subject;
    @NonNull
    private String template;
    private Map< String, Object > model;
}
