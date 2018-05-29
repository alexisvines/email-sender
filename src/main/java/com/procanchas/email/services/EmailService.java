package com.procanchas.email.services;

import com.procanchas.email.model.Mail;
import freemarker.template.TemplateException;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * Interfaz de envio de correos
 */
public interface EmailService {

    void sendEmail(Mail mail) throws MessagingException, IOException, TemplateException;
}
