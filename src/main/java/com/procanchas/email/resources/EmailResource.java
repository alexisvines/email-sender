package com.procanchas.email.resources;

import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import freemarker.template.Configuration;
import org.springframework.web.bind.annotation.RestController;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1.0/email")
public class EmailResource {

    private static final String API_EMAIL = "/test";


    @Autowired
    private JavaMailSender sender;

    @Autowired
    private Configuration freemarkerConfig;


    /**
     *
     * @return
     */
    @RequestMapping(value = API_EMAIL,
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    String envioCorreo() {
        try {
            sendEmail6();
            return "Email enviado!";
        } catch (Exception ex) {
            return "Error in sending email: " + ex;
        }
    }


    /**
     * Metodo que envia el correo
     */
    private void sendEmail() throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setTo("alexis.freire.leiva@gmail.com");
        helper.setText("Canchas Mail");
        helper.setSubject("[Bienvenido a Canchas]");

        sender.send(message);
    }



    /**
     * Metodo que envia el correo con un attach
     */
    private void sendEmail2() throws MessagingException {
        MimeMessage message = sender.createMimeMessage();

        // Enable the multipart flag
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setTo("alexis.freire.leiva@gmail.com");
        helper.setText("Canchas Mail");
        helper.setSubject("[Bienvenido a Canchas]");

        ClassPathResource file = new ClassPathResource("nuevo.txt");
        helper.addAttachment("nuevo.txt", file);

        sender.send(message);


    }


    /**
     *
     * @throws Exception
     */
    private void sendEmail3() throws Exception {
        MimeMessage message = sender.createMimeMessage();

        // Enable the multipart flag!
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo("alexis.freire.leiva@gmail.com");
        helper.setText("<html><body>Here is a cat picture! <img src='cid:id101'/><body></html>", true);
        helper.setSubject("[Bienvenido a " +
                "Canchas]");

        ClassPathResource file = new ClassPathResource("cat.jpg");
        helper.addInline("id101", file);

        sender.send(message);
    }

    /**
     *
     * @throws Exception
     */
    private void sendEmail5() throws Exception {


    }

    /**
     *
     * @throws Exception
     */
    private void sendEmail6() throws Exception {
        MimeMessage message = sender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message,MimeMessageHelper.MULTIPART_MODE_MIXED,StandardCharsets.UTF_8.name());

        Map<String, Object> model = new HashMap<>();
        model.put("usuario", "Alexis Sanchez");
        model.put("nro_cancha", "Nº4");
        model.put("valor_cancha","20.500");

        // set loading location to src/main/resources
        // You may want to use a subfolder such as /templates here
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/");

        Template t = freemarkerConfig.getTemplate("venta.ftl");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        helper.setTo("alexis.freire@procanchas.cl");
        helper.setText(text,true); // set to html
        helper.setSubject("Hi");

        sender.send(message);
    }


}
