package com.procanchas.email.resources;

import com.procanchas.email.model.EmailStatus;
import com.procanchas.email.model.Mail;
import com.procanchas.email.services.EmailService;
import com.procanchas.email.utils.CustomErrorType;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1.0/email")
public class EmailResource {

    private static final String API_EMAIL = "/send";
    private static final String SUCCESSFUL_SEND = "Correo Enviado Satisfactoriamente";
    private static final String ERROR_SEND = "Correo no se ha podido enviar";

    @Autowired
    private EmailService _emailService;


    /**
     *
     * Metodo para enviar correos con template freemarker
     * que dado un email y el nombre de un template,
     * que este incorporado en la carpeta de templates se enviara a destino
     *
     * @param emailTo
     * @param subject
     * @param templateId
     * @return
     */
    @RequestMapping(value = API_EMAIL,
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET ,
            headers = "Accept=application/json")
    public ResponseEntity<?> sendAFreemarkerTemplateEmail(@RequestParam(value = "emailTo", required = true) String emailTo,
                                                          @RequestParam(value = "subject", required = false) String subject,
                                                          @RequestParam(value = "templateId", required = true) Long templateId) {

        try {
            log.info("Inicio de envio de correo con un ejemplo de Freemarker HTML Template");
            log.info("emailTo:{}, subject:{}, templateName:{}",emailTo,subject,templateId);
            validateInputs(emailTo, templateId);

            Mail mail = new Mail(emailTo,subject,templateId);

            _emailService.sendEmail(mail);

        } catch (MessagingException e) {
            log.error("Ha ocurrido un error al enviar el correo",e);
            return new ResponseEntity(
                    new CustomErrorType("Ha ocurrido un error al enviar el correo "),
                    HttpStatus.NO_CONTENT);
        } catch (IOException e) {
            log.error("Ha ocurrido un error al enviar el correo",e);
            return new ResponseEntity(
                    new CustomErrorType("Ha ocurrido un error al enviar el correo "),
                    HttpStatus.NO_CONTENT);

        } catch (TemplateException e) {
            log.error("Ha ocurrido un error al enviar el correo",e);
            return new ResponseEntity(
                    new CustomErrorType("Ha ocurrido un error al enviar el correo "),
                    HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            log.error("Ha ocurrido un error al enviar el correo",e);
            return new ResponseEntity(
                    new CustomErrorType("Ha ocurrido un error al enviar el correo "),
                    HttpStatus.EXPECTATION_FAILED);
        }

        log.info("Mensaje Enviado correctamente");
        return new ResponseEntity<EmailStatus>(new EmailStatus(0,"Mensaje Enviado"),HttpStatus.OK);

    }

    /**
     * Metodo que se encarga de realizar las validaciones de los datos de entrada
     * @param emailTo
     * @param idTemplate
     */
    private void validateInputs(String emailTo, Long
            idTemplate) throws Exception {

        if(emailTo == null || emailTo.isEmpty()){
            throw  new Exception("El campo emailTo es requerido");
        }


        if(idTemplate == null){
            throw  new Exception("El campo templateName es requerido");

        }
    }
}
