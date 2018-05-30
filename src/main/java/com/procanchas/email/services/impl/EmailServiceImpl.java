package com.procanchas.email.services.impl;


import com.procanchas.email.model.Mail;
import com.procanchas.email.model.User;
import com.procanchas.email.repository.ConfigurationRepository;
import com.procanchas.email.services.EmailService;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import freemarker.template.Configuration;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Qualifier("freeMarkerConfiguration")
    @Autowired
    private Configuration freemarkerConfig;

    @Override
    public void sendEmail(Mail mail) throws MessagingException, IOException, TemplateException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        Template t = freemarkerConfig.getTemplate(mail.getTemplate());
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, mail);
        helper.setTo(mail.getTo());
        helper.setText(html,true);
        helper.setSubject(mail.getSubject());

        emailSender.send(message);
    }
}
