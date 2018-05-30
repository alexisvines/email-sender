package com.procanchas.email.resources;

import com.procanchas.email.model.Mail;
import com.procanchas.email.services.EmailService;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1.0/email")
public class EmailResource {

    private static final String API_EMAIL = "/test";
    private static final String SUCCESSFUL_SEND = "Correo Enviado Satisfactoriamente";
    private static final String ERROR_SEND = "Correo no se ha podido enviar";

    @Autowired
    private EmailService emailService;


    /**
     * Api inicial para probar el envio de correo desde templates del antiguo monolito
     * @return
     */
    @RequestMapping(value = API_EMAIL,
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    String sendAnTemplateEmail() {

            log.info("Inicio de envio de correo con un ejemplo de Freemarker HTML Template");


            Mail mail = new Mail();
            mail.setFrom("testcanchas@gmail.com");
            mail.setTo("alexis.freire@procanchas.cl");
            mail.setSubject("[Email de pruebas] canchas");
            mail.setTemplate("mail-anulacion-turno.html");

            Map model = new HashMap();
            model.put("clubName", "Futbol Soccer");
            mail.setModel(model);

        try {
            emailService.sendEmail(mail);
        } catch (MessagingException e) {
            log.error("Ha ocurrido un error al enviar el correo",e);
            return ERROR_SEND;

        } catch (IOException e) {
            log.error("Ha ocurrido un error al enviar el correo",e);
            return ERROR_SEND;
        } catch (TemplateException e) {
            log.error("Ha ocurrido un error al enviar el correo",e);
            return ERROR_SEND;

        }


       return SUCCESSFUL_SEND;
    }


}
