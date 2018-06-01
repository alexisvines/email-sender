/*package ar.com.procanchas.service;

import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.Locale;


import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import ar.com.procanchas.domain.*;
import ar.com.procanchas.repository.ComboPackageRepository;
import ar.com.procanchas.repository.OrderRepository;
import ar.com.procanchas.repository.UserRepository;
import ar.com.procanchas.service.dto.*;
import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import ar.com.procanchas.service.util.AppointmentUtils;
import ar.com.procanchas.service.util.PaymentUtils;
import ar.com.procanchas.web.rest.vm.TemporalUserVM;
import io.github.jhipster.config.JHipsterProperties;

/**
 * Service for sending emails.
 * <p>
 * We use the @Async annotation to send emails asynchronously.
 * </p>
 *
@Service
@RestController
public class EmailServiceMonolitico {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String USER = "user";

    private static final String BASE_URL = "baseUrl";

    private static final String BASE_URL_BACKEND = "baseUrlBackend";

    private static final String PAYMENT = "payment";

    private static final String PAYMENT_URL = "paymentUrl";

    private final JHipsterProperties jHipsterProperties;

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;


    @Value("${procanchas.backendHost}")
    private String backendHost;

    @Value("${isProduction}")
    private Boolean isProduction;

    @Value("${procanchas.default.correoVentas}")
    private String eMailVentas;


    public MailService(JHipsterProperties jHipsterProperties, JavaMailSender javaMailSender,
                       MessageSource messageSource, SpringTemplateEngine templateEngine) {

        this.jHipsterProperties = jHipsterProperties;
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
    }

    /**
     * Email sender method
     * @param to Recepcionist ot the mail
     * @param subject Subject of the email
     * @param content Content of the email
     * @param isMultipart is multipart ??
     * @param isHtml is a html content
     *
    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}", isMultipart,
                isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);
            message.setTo(to);
            message.setFrom(new InternetAddress(jHipsterProperties.getMail().getFrom(), "ProCanchas"));
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);
        } catch (Exception e) {
            log.warn("Email could not be sent to user '{}'", to, e);
        }
    }

    /**
     * Send an activation Email to the user
     * @param user User to send the email
     *
    @Async
    public void sendActivationEmail(User user) {
        log.debug("Sending activation email to '{}'", user.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        // String content = templateEngine.process("activationEmail", context);
        String content = templateEngine.process("mail-manual-register", context);
        String subject = messageSource.getMessage("email.activation.title", null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    /**
     * Send welcome email to the user
     * @param user User to send the email
     *
    @Async
    public void sendWelcomeEmail(User user) {
        log.debug("Sending welcome email to '{}'", user.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        // String content = templateEngine.process("activationEmail", context);
        String content = templateEngine.process("mail-welcome", context);
        String subject = messageSource.getMessage("email.welcome.title", null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }


    /**
     * Send the password reset email
     * @param user user to the the email
     *
    @Async
    public void sendPasswordResetMail(User user) {
        log.debug("Sending password reset email to '{}'", user.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process("mail-reset-password", context);
        String subject = messageSource.getMessage("email.reset.title", null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    /**
     * Send registeration validation Email
     * @param user user to send the email
     * @param provider provider ??? ni idea !
     *
    @Async
    public void sendSocialRegistrationValidationEmail(User user, String provider) {
        log.debug("Sending social registration validation email to '{}'", user.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable("provider", StringUtils.capitalize(provider));
        String content = templateEngine.process("mail-anulacion-turno", context);
        String subject = messageSource.getMessage("email.social.registration.title", null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }





    /**
     * Send a order payment email
     * @param order Order data used in the email
     *
    @Async
    public void sendProcanchasPaymentEmail(Order order) {
        User user = null;
        if(order.getPlayer() != null && order.getPlayer().getUser() != null){
            user = order.getPlayer().getUser();
            if(user.getRutNumber() == null){
                user.setRutNumber(11111111);
                user.setRutValidator("1");
            }
        }else{
            user = new User();
            user.setFirstName(order.getTemporalUser().getFirstName());
            if(order.getTemporalUser().getLastName() == null){
                order.getTemporalUser().setLastName("");
            }
            user.setLastName(order.getTemporalUser().getLastName());
            user.setRutNumber(order.getTemporalUser().getRutNumber());
            user.setRutValidator(order.getTemporalUser().getRutValidator());
            user.setEmail(order.getTemporalUser().getEmail());
        }


        Locale locale = Locale.forLanguageTag(user.getLangKey() != null ? user.getLangKey(): "es");
        Context context = new Context(locale);
        // WebContext context = new WebContext(request, response,
        // servletContext, locale);

        PaymentMailDTO dto = new PaymentMailDTO(order.getPayment().getComboPackage().getCodeQr(),
                order.getPayment().getComboPackage().getQrValue(),
                order.getPayment().getComboPackage().getClub().getUrlLogo(),
                order.getPayment().getComboPackage().getClub().getName(),
                order.getPayment().getComboPackage().getClub().getAddress(),
                order.getPayment().getComboPackage().getClub().getCity().getName(),
                order.getPayment().getComboPackage().getAppointment().getDate(),
                order.getPayment().getComboPackage().getAppointment().getTime(),
                order.getPayment().getComboPackage().getAppointment().getEndTime(),
                order.getPayment().getComboPackage().getAppointment().getTime().minusMinutes(15L),
                order.getPayment().getComboPackage().getAppointment().getField().getNumber().toString(),
                order.getPayment().getComboPackage().getAppointment().getField().getComfortFeatures(),
                (order.getPayment().getDiscount() == null) ? ""
                        : order.getPayment().getDiscount().getDiscountType().getPercentage().toString(),
                (order.getPayment().getDiscount() == null) ? 0D
                        : AppointmentUtils.calculateDiscount(order.getPayment().getComboPackage().getAppointment(),
                        order.getPayment().getDiscount().getDiscountType().getPercentage()),
                order.getPayment().getComboPackage().getTotal(), order.getPayment().getComboPackage().getClub().getPhone(),
                user.getFirstName()+" "+user.getLastName(), user.getEmail(),
                order.getPayment().getComboPackage().getAppointment().getField().getFieldType().getName(),
                user.getRutNumber().toString().concat("-").concat(user.getRutValidator()), order.getPayment().getId().toString(),
                order.getPayment().getDate(), order.getPayment().getComboPackage().getAppointment().getId().toString(), order.getPayment().getPaymentMethod().getName());



        log.debug("Sending Reserve email to '{}'", user.getEmail());
        context.setVariable(PAYMENT, dto);
        //context.setVariable(USER, user != null ? user : temporalUser);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        context.setVariable(BASE_URL_BACKEND, this.backendHost);
        // String content = templateEngine.process("activationEmail", context);
        String content = templateEngine.process("mail-procanchas-payment-voucher", context);
        Object[] params = new Object[1];
        params[0] = order.getId();
        String subject = messageSource.getMessage("email.payment.title", params, locale);
        sendEmail(user.getEmail() , subject, content, false, true);

        //Se va a mandar un email a raul.
        String subjectRaul = messageSource.getMessage("email.payment.test", params, locale);
        sendEmail(eMailVentas, subjectRaul, content,false, true);



        //Este mail se manda a todo los encargados de los clubes.
        context = new Context(locale);
        context.setVariable(PAYMENT, dto);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        context.setVariable(BASE_URL_BACKEND, this.backendHost);
        content = templateEngine.process("mail-confirmacion-canchas", context);



        sendConfirmationEmailPaymentClub(order.getPayment().getComboPackage().getClub().getPaymentNotifyEmails()
                ,content
                ,order.getPayment().getComboPackage().getAppointment().getDate().toString(),
                order.getPayment().getComboPackage().getAppointment().getTime().toString());
    }

    @Async
    public void sendConfirmationEmailPaymentClub(Set<String> subjects, String content, String fecha, String hora){
        if (this.isProduction)
            for (String s : subjects) {
                sendEmail(s,"Compra ProCanchas: "+fecha+" + "+hora+"hs",content,false,true);
            }
    }







    /**
     * Send a order payment email
     * @param order Order data used in the email
     *
    @Async
    public void sendProcanchasPaymentEmailRecepcionist(Order order) {
        User user = null;
        if(order.getPlayer() != null && order.getPlayer().getUser() != null){
            user = order.getPlayer().getUser();
            if(user.getRutNumber() == null){
                user.setRutNumber(11111111);
                user.setRutValidator("1");
            }

            if(user.getLastName() == null){
                user.setLastName("");
            }
        }else{
            user = new User();
            user.setFirstName(order.getTemporalUser().getFirstName());
            if(order.getTemporalUser().getLastName() == null){
                order.getTemporalUser().setLastName("");
            }
            user.setLastName(order.getTemporalUser().getLastName());
            user.setRutNumber(order.getTemporalUser().getRutNumber());
            user.setRutValidator(order.getTemporalUser().getRutValidator());
            user.setEmail(order.getTemporalUser().getEmail());
        }

        if(order.getReceptionist().getUser().getFirstName() == null)
            order.getReceptionist().getUser().setFirstName("");

        if(order.getReceptionist().getUser().getLastName() == null)
            order.getReceptionist().getUser().setLastName("");


        Locale locale = Locale.forLanguageTag(user.getLangKey() != null ? user.getLangKey(): "es");
        Context context = new Context(locale);
        // WebContext context = new WebContext(request, response,
        // servletContext, locale);

        PaymentMailDTO dto = new PaymentMailDTO(order.getPayment().getComboPackage().getCodeQr(),
                order.getPayment().getComboPackage().getQrValue(),
                order.getPayment().getComboPackage().getClub().getUrlLogo(),
                order.getPayment().getComboPackage().getClub().getName(),
                order.getPayment().getComboPackage().getClub().getAddress(),
                order.getPayment().getComboPackage().getClub().getCity().getName(),
                order.getPayment().getComboPackage().getAppointment().getDate(),
                order.getPayment().getComboPackage().getAppointment().getTime(),
                order.getPayment().getComboPackage().getAppointment().getEndTime(),
                order.getPayment().getComboPackage().getAppointment().getTime().minusMinutes(15L),
                order.getPayment().getComboPackage().getAppointment().getField().getNumber().toString(),
                order.getPayment().getComboPackage().getAppointment().getField().getComfortFeatures(),
                (order.getPayment().getDiscount() == null) ? ""
                        : order.getPayment().getDiscount().getDiscountType().getPercentage().toString(),
                (order.getPayment().getDiscount() == null) ? 0D
                        : AppointmentUtils.calculateDiscount(order.getPayment().getComboPackage().getAppointment(),
                        order.getPayment().getDiscount().getDiscountType().getPercentage()),
                order.getPayment().getComboPackage().getTotal(), order.getPayment().getComboPackage().getClub().getPhone(),
                user.getFirstName()+" "+user.getLastName(), user.getEmail(),
                order.getPayment().getComboPackage().getAppointment().getField().getFieldType().getName(),
                user.getRutNumber().toString().concat("-").concat(user.getRutValidator()), order.getPayment().getId().toString(),
                order.getPayment().getDate(), order.getPayment().getComboPackage().getAppointment().getId().toString(), order.getPayment().getPaymentMethod().getName());


        log.debug("Sending Reserve email to '{}'", user.getEmail());
        context.setVariable(PAYMENT, dto);
        //context.setVariable(USER, user != null ? user : temporalUser);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        context.setVariable(BASE_URL_BACKEND, this.backendHost);
        // String content = templateEngine.process("activationEmail", context);
        String content = templateEngine.process("mail-payment-button-voucher", context);
        Object[] params = new Object[1];
        params[0] = order.getId();
        String subject = messageSource.getMessage("email.payment.title", params, locale);
        sendEmail(user.getEmail() , subject, content, false, true);

        if(order.getPayment().getPaymentMethod().getName().equals("Transbank WebPayPlus Debito")){
            String RaulSubject = messageSource.getMessage("email.reserve.test", params, locale);
            sendEmail(eMailVentas, RaulSubject, content, false, true);}

    }

    /**
     * Email used to cancel a reservation
     * @param comboPackage Combopackage used in the email
     * @param player Player used in the email
     * @param appointment Appointment info used in the email
     *
    @Async
    public void cancelReservationEmail(ComboPackage comboPackage, Player player, Appointment appointment) {
        User user = player.getUser();
        log.debug("Sending welcome email to '{}'", user.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        // WebContext context = new WebContext(request, response,
        // servletContext, locale);
        Payment paymentWithDiscount = PaymentUtils.getPaymentWithDiscount(comboPackage.getPayments());
        PaymentMailDTO dto = new PaymentMailDTO(comboPackage.getCodeQr(), comboPackage.getQrValue(),
                comboPackage.getClub().getUrlLogo(), comboPackage.getClub().getName(),
                comboPackage.getClub().getAddress(), comboPackage.getClub().getCity().getName(), appointment.getDate(),
                appointment.getTime(), appointment.getEndTime(), appointment.getTime().minusMinutes(15L),
                appointment.getField().getNumber().toString(), appointment.getField().getComfortFeatures(),
                (paymentWithDiscount == null) ? ""
                        : paymentWithDiscount.getDiscount().getDiscountType().getPercentage().toString(),
                (paymentWithDiscount == null) ? 0D
                        : AppointmentUtils.calculateDiscount(appointment,
                        paymentWithDiscount.getDiscount().getDiscountType().getPercentage()),
                appointment.getPrice(), comboPackage.getClub().getPhone(),
                appointment.getPlayer().getUser().getFirstName()+" "+appointment.getPlayer().getUser().getLastName(),
                appointment.getPlayer().getUser().getEmail(), appointment.getField().getFieldType().getName(),
                appointment.getPlayer().getUser().getRutNumber().toString().concat("-").concat(appointment.getPlayer().getUser().getRutValidator()), null, null , null, null);

        context.setVariable(PAYMENT, dto);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        context.setVariable(BASE_URL_BACKEND, this.backendHost);
        // String content = templateEngine.process("activationEmail", context);
        String content = templateEngine.process("mail-anular", context);
        Object[] params = new Object[1];
        params[0] = comboPackage.getQrValue();
        String subject = messageSource.getMessage("email.cancel.title", params, locale);
        sendEmail(user.getEmail(), subject, content, false, true);

    }

//Nacho

    @Async
    public void cancelReceptionistReservationEmail(ComboPackage comboPackage, User recepcionista, TemporalUser player,Appointment appointment) {

        log.debug("Sending anulacion email to '{}'", recepcionista.getEmail());

        Locale locale = Locale.forLanguageTag(recepcionista.getLangKey());

        Payment paymentWithDiscount = PaymentUtils.getPaymentWithDiscount(comboPackage.getPayments());
        PaymentMailDTO dto = new PaymentMailDTO(comboPackage.getCodeQr(), comboPackage.getQrValue(),
                comboPackage.getClub().getUrlLogo(), comboPackage.getClub().getName(),
                comboPackage.getClub().getAddress(), comboPackage.getClub().getCity().getName(), appointment.getDate(),
                appointment.getTime(), appointment.getEndTime(), appointment.getTime().minusMinutes(15L),
                appointment.getField().getNumber().toString(), appointment.getField().getComfortFeatures(),
                (paymentWithDiscount == null) ? ""
                        : paymentWithDiscount.getDiscount().getDiscountType().getPercentage().toString(),
                (paymentWithDiscount == null) ? 0D
                        : AppointmentUtils.calculateDiscount(appointment,
                        paymentWithDiscount.getDiscount().getDiscountType().getPercentage()),
                appointment.getPrice(), comboPackage.getClub().getPhone(),
                appointment.getPlayer().getUser().getFirstName()+" "+appointment.getPlayer().getUser().getLastName(),
                appointment.getPlayer().getUser().getEmail(), appointment.getField().getFieldType().getName(),
                appointment.getPlayer().getUser().getRutNumber().toString().concat("-").concat(appointment.getPlayer().getUser().getRutValidator()), null, null , null, null);

        Context context = new Context(locale);
        context.setVariable(PAYMENT, dto);

        //  context.setVariable(PAYMENT, dto);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        context.setVariable(BASE_URL_BACKEND, this.backendHost);
        String content = templateEngine.process("mail-anulacion-turno", context);
        Object[] params = new Object[1];
        params[0]= "params 0";
        // String subject = messageSource.getMessage("email.cancel.title", params, locale);
        sendEmail(recepcionista.getEmail(), "Procanchas - Turno anulado", content, false, true);
        sendEmail(player.getEmail(), "Procanchas - Turno anulado", content, false, true);
    }

    @Autowired
    OrderRepository orderRepository;
    @Autowired UserRepository userRepository;
    @GetMapping("/mockEmail")
    public void status(){
        cancelReceptionistReservationEmail(null,null,null,null);
    }

    //Nacho
    @Async
    public void cancelTemporalUserReservationEmail(ComboPackage comboPackage, String mail) {

        log.debug("Sending welcome email to '{}'", mail);
        Locale locale = Locale.forLanguageTag(mail);

        Object[] params = new Object[1];

        String subject = messageSource.getMessage("email.anular.title", params, locale);
        sendEmail(mail, subject, "Hola mundo desde la cancelacion de la orden", false, true);

    }

    /**
     * Send a Procanchas Promotional Release Email
     * @param user User data
     *
    @Async
    public void sendReleasePromotionalEmail(User user) {
        log.debug("Sending Release email to '{}'", user.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process("mail-lanzamiento", context);
        String subject = messageSource.getMessage("email.promotional.title", null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }


    /**
     * Send a Procanchas only reserve mail
     * @param order Order
     *
    @Async
    public void sendReserveMailWithoutMail(Order order) {
        //TODO ENCAPSULAR ESTO EN UNA FUNCIONALIDAD
        User user = null;
        if(order.getPlayer() != null && order.getPlayer().getUser() != null){
            user = order.getPlayer().getUser();
            if(user.getRutNumber() == null){
                user.setRutNumber(1);
                user.setRutValidator("1");
            }

            if(user.getLastName() == null){
                user.setLastName("");
            }
        }else{
            user = new User();
            user.setFirstName(order.getTemporalUser().getFirstName());
            if(order.getTemporalUser().getLastName() == null){
                order.getTemporalUser().setLastName("");
            }
            user.setLastName(order.getTemporalUser().getLastName());
            user.setPhone(order.getTemporalUser().getPhone());
            user.setRutNumber(order.getTemporalUser().getRutNumber());
            user.setRutValidator(order.getTemporalUser().getRutValidator());
            user.setEmail(order.getTemporalUser().getEmail());
        }

        if(order.getReceptionist().getUser().getFirstName() == null)
            order.getReceptionist().getUser().setFirstName("");

        if(order.getReceptionist().getUser().getLastName() == null)
            order.getReceptionist().getUser().setLastName("");

        log.debug("Sending Reserve email without mail to '{}'", order.getPlayer().getUser().getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey() != null ? user.getLangKey(): "es");
        Context context = new Context(locale);
        context.setVariable(USER, order.getPlayer().getUser());
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        Object[] params = new Object[1];
        params[0] = order.getId();
        String content = templateEngine.process("mail-reserve-voucher-without-email.html", context);
        String subject = messageSource.getMessage("email.reserve.title", params, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    /**
     * Send a Procanchas field reserve with no pay
     * @param order Order
     *
    @Async
    public void sendReserveMailWithEmail(Order order) {

        User user = null;
        if(order.getPlayer() != null && order.getPlayer().getUser() != null){
            user = order.getPlayer().getUser();
            if(user.getRutNumber() == null){
                user.setRutNumber(11111111);
                user.setRutValidator("1");
            }

            if(user.getLastName() == null){
                user.setLastName("");
            }
        }else{
            user = new User();
            user.setFirstName(order.getTemporalUser().getFirstName());
            if(order.getTemporalUser().getLastName() == null){
                order.getTemporalUser().setLastName("");
            }
            user.setLastName(order.getTemporalUser().getLastName());
            user.setRutNumber(order.getTemporalUser().getRutNumber());
            user.setRutValidator(order.getTemporalUser().getRutValidator());
            user.setEmail(order.getTemporalUser().getEmail());
        }

        if(order.getReceptionist().getUser().getFirstName() == null)
            order.getReceptionist().getUser().setFirstName("");

        if(order.getReceptionist().getUser().getLastName() == null)
            order.getReceptionist().getUser().setLastName("");


        ReserveMailDTO dto = new ReserveMailDTO(user.getFirstName().concat(" ").concat(user.getLastName()),
                order.getId().toString(), order.getPayment().getComboPackage().getAppointment().getField().getFieldType().getName(),
                order.getPayment().getComboPackage().getAppointment().getField().getNumber().toString(),
                order.getPayment().getComboPackage().getAppointment().getDate(),
                order.getPayment().getComboPackage().getAppointment().getTime(),
                order.getPayment().getComboPackage().getAppointment().getEndTime(),
                order.getPayment().getComboPackage().getAppointment().getTime().minusMinutes(15),
                order.getPayment().getComboPackage().getAppointment().getClubPrice(),
                order.getPayment().getComboPackage().getClub().getName(),
                order.getPayment().getComboPackage().getClub().getAddress(),
                order.getPayment().getComboPackage().getClub().getPhone(),
                user.getEmail(), order.getReceptionist().getUser().getFirstName().concat(" ").concat(order.getReceptionist().getUser().getLastName()), order.getOrderDate(),
                order.getPayment().getComboPackage().getClub().getUrlLogo()  );

        log.debug("Sending Reserve email to '{}'", user.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey() != null ? user.getLangKey(): "es");
        Context context = new Context(locale);
        context.setVariable(PAYMENT, dto);
        context.setVariable(BASE_URL_BACKEND, this.backendHost);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        Object[] params = new Object[1];
        params[0] = order.getId();
        String content = templateEngine.process("mail-reserve-voucher-with-email", context);
        String subject = messageSource.getMessage("email.reserve.title", params, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    /**
     * Send a payment button email
     * @param order order used in the payment
     *
    @Async
    public void sendPayButtonEmail(Order order){

        User user = null;
        if(order.getPlayer() != null && order.getPlayer().getUser() != null){
            user = order.getPlayer().getUser();
            if(user.getRutNumber() == null){
                user.setRutNumber(11111111);
                user.setRutValidator("1");
            }

            if(user.getLastName() == null){
                user.setLastName("");
            }
        }else{
            user = new User();
            user.setFirstName(order.getTemporalUser().getFirstName());
            if(order.getTemporalUser().getLastName() == null){
                order.getTemporalUser().setLastName("");
            }
            user.setLastName(order.getTemporalUser().getLastName());
            user.setRutNumber(order.getTemporalUser().getRutNumber());
            user.setRutValidator(order.getTemporalUser().getRutValidator());
            user.setEmail(order.getTemporalUser().getEmail());
        }

        if(order.getReceptionist().getUser().getFirstName() == null)
            order.getReceptionist().getUser().setFirstName("");

        if(order.getReceptionist().getUser().getLastName() == null)
            order.getReceptionist().getUser().setLastName("");

        PaymentButtonDTO paymentButtonDTO = new PaymentButtonDTO(order.getPayment().getComboPackage().getClub().getUrlLogo(),
                order.getPayment().getComboPackage().getClub().getName(),
                order.getPayment().getComboPackage().getClub().getPhone(),
                order.getPayment().getComboPackage().getClub().getAddress(),
                order.getPayment().getComboPackage().getQrValue(), user.getFirstName().concat(" ").concat(user.getLastName()),
                order.getPayment().getComboPackage().getAppointment().getField().getFieldType().getName(),
                order.getPayment().getComboPackage().getAppointment().getField().getNumber().toString(),
                order.getPayment().getComboPackage().getAppointment().getClubPrice(), order.getPayment().getComboPackage().getAppointment().getDate(),
                order.getReceptionist().getUser().getFirstName().concat(" ").concat(order.getReceptionist().getUser().getLastName()),
                order.getPayment().getComboPackage().getAppointment().getTime(), order.getPayment().getComboPackage().getAppointment().getEndTime(),
                order.getPayment().getComboPackage().getAppointment().getTime().minusMinutes(15L));

        log.debug("Sending Pay button email to '{}'", user.getEmail());

        StringBuilder sb = new StringBuilder("/pagos/?order=")
                .append(order.getComboPackages().iterator().next().getQrValue());

        Locale locale = Locale.forLanguageTag(user.getLangKey() != null ? user.getLangKey(): "es");
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(PAYMENT, paymentButtonDTO);
        context.setVariable(PAYMENT_URL, sb);
        context.setVariable(BASE_URL_BACKEND, this.backendHost);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process("mail-payment-button", context);
        Object[] params = new Object[1];
        params[0] = order.getId();
        String subject = messageSource.getMessage("email.paymentbutton.title", params, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    /**
     * Send a payment email using the bank transfer template email engine
     * @param order order to render in the email
     *
    @Async void sendBankTransferEmail(Order order){
        User user = null;
        if(order.getPlayer() != null && order.getPlayer().getUser() != null){
            user = order.getPlayer().getUser();
            if(user.getRutNumber() == null){
                user.setRutNumber(11111111);
                user.setRutValidator("1");
            }

            if(user.getLastName() == null){
                user.setLastName("");
            }
        }else{
            user = new User();
            user.setFirstName(order.getTemporalUser().getFirstName());
            if(order.getTemporalUser().getLastName().isEmpty()){
                order.getTemporalUser().setLastName("");
            }
            user.setLastName(order.getTemporalUser().getLastName());
            user.setRutNumber(order.getTemporalUser().getRutNumber());
            user.setRutValidator(order.getTemporalUser().getRutValidator());
            user.setEmail(order.getTemporalUser().getEmail());
        }

        if(order.getReceptionist().getUser().getFirstName() == null)
            order.getReceptionist().getUser().setFirstName("");

        if(order.getReceptionist().getUser().getLastName() == null)
            order.getReceptionist().getUser().setLastName("");

        BankTransferEmailDTO dto = new BankTransferEmailDTO(user.getFirstName().concat(" ").concat(user.getLastName()),
                order.getPayment().getComboPackage().getClub().getName(),
                order.getPayment().getComboPackage().getClub().getAddress(),
                order.getPayment().getComboPackage().getClub().getUrlLogo(),
                order.getPayment().getComboPackage().getAppointment().getField().getFieldType().getName(),
                order.getPayment().getComboPackage().getAppointment().getField().getNumber().toString(),
                order.getPayment().getComboPackage().getAppointment().getClubPrice(),
                order.getPayment().getComboPackage().getAppointment().getDate(),
                order.getPayment().getComboPackage().getAppointment().getTime(),
                order.getPayment().getComboPackage().getAppointment().getEndTime(),
                order.getPayment().getComboPackage().getReceptionist().getUser().getFirstName().concat(" ").concat(order.getPayment().getComboPackage().getReceptionist().getUser().getLastName()),
                order.getPayment().getComboPackage().getClub().getClubBankAccount().getBank().getName(),
                order.getPayment().getComboPackage().getClub().getClubBankAccount().getAccountRut(),
                order.getPayment().getComboPackage().getClub().getClubBankAccount().getBankAccountType().getName(),
                order.getPayment().getComboPackage().getClub().getClubBankAccount().getAccountNumber(),
                order.getPayment().getComboPackage().getClub().getClubBankAccount().getConfirmationEmail(),
                order.getPayment().getComboPackage().getClub().getClubBankAccount().getAccountName());


        log.debug("Sending Bank Transfer email to '{}'", user.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey() != null ? user.getLangKey(): "es");
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(PAYMENT, dto);
        context.setVariable(BASE_URL_BACKEND, this.backendHost);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        Object[] params = new Object[1];
        params[0] = order.getId();
        String content = templateEngine.process("mail-transfer", context);
        String subject = messageSource.getMessage("email.banktransfer.title", params, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }
}*/
