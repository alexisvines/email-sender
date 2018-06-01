package com.procanchas.email.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Clase utilitaria para realizar operaciones comunes sobre los emails
 */
@Slf4j
@UtilityClass
public class MailCommonsUtils {


    public  boolean isValidEmail(String email) {
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

    public String getHtmlMail(){
        return ("<html><head><body><h1> Let Down! : :) ${to}</h1> <br><img src=\"http://localhost:8080/mario.png\"  height=\"42\" width=\"42\"></body></head></html>");
    }

    public String getKeyAttribute(){
        return ("user");
    }


    public File getTemporalFile(String pFilename, String sb) throws IOException {
        File tempFile = File.createTempFile(pFilename, ".html");
        FileWriter fileWriter = new FileWriter(tempFile, true);
        BufferedWriter bw = new BufferedWriter(fileWriter);
        bw.write(sb);
        bw.close();
        return tempFile;
    }



}
