package com.procanchas.email.services.impl;


import com.procanchas.email.entity.EmailTemplate;
import com.procanchas.email.entity.EmailTemplateLabel;
import com.procanchas.email.model.Mail;
import com.procanchas.email.model.User;
import com.procanchas.email.repository.ConfigurationRepository;
import com.procanchas.email.repository.EmailTemplateRepository;
import com.procanchas.email.services.EmailService;
import com.procanchas.email.utils.TemplateUtils;
import com.sun.mail.imap.protocol.MailboxInfo;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import freemarker.template.Configuration;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Implementacion del servicio de envio de correo
 */
@EnableAsync
@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    EmailTemplateRepository emailTemplateRepository;

    @Qualifier("freeMarkerConfiguration")
    @Autowired
    private Configuration freemarkerConfig;


    /**
     *
     * @param mail
     * @throws MessagingException
     * @throws IOException
     * @throws TemplateException
     */
    @Async
    @Override
    public void sendEmail(Mail mail) throws MessagingException, IOException, TemplateException {
        /*MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        // TODO: Obtener la plantilla desde bd

        Template t = freemarkerConfig.getTemplate("mail-lanzamiento.html");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, mail);
        helper.setTo(mail.getTo());
        helper.setText(html,true);
        helper.setSubject(mail.getSubject());*/

        EmailTemplate emailTemplate = new EmailTemplate();
        emailTemplate.setHtml("<html><head><body><h1> Let Down! : :)</h1></body></head></html>");
        EmailTemplateLabel etl = new EmailTemplateLabel();
        etl.setLabel("{user.email}");
        emailTemplate.getEmailTemplateLabels().add(etl);
        emailTemplate.setSubject("Alexis");
        log.info("{}",emailTemplate);

        emailTemplateRepository.save(emailTemplate);


        //emailSender.send(message);
    }


}
