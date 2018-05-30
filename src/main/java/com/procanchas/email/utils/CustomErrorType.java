package com.procanchas.email.utils;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author Alexis Freire
 * Mensaje generico para errores customizables de envio de correo
 */
@Data
@AllArgsConstructor
public class CustomErrorType {

    private String errorMessage;


}