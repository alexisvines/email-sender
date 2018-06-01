package com.procanchas.email.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Clase utilitaria para realizar operaciones comunes sobre los emails
 */
@Slf4j
@UtilityClass
public class MailCommonsUtils {


    public static boolean isValidEmail(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(java.net.IDN.toASCII(email));
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        System.out.println("==> "+result+" : "+email);
        return result;
    }
    
}
