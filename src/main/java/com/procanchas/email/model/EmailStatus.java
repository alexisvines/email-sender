package com.procanchas.email.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailStatus {

    private int codigo;
    private String mensaje;
}