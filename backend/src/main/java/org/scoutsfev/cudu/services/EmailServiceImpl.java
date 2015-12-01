package org.scoutsfev.cudu.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.util.Locale;
import java.util.UUID;

@Service
@Profile("production")
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final MessageSource messageSource;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine, MessageSource messageSource, ApplicationEventPublisher eventPublisher) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.messageSource = messageSource;
        this.eventPublisher = eventPublisher;
    }

    @Async
    @Override
    public void enviarMailCambioContraseña(String nombre, String email, String token, Locale locale) {
        try {
            final Context ctx = new Context(locale);
            ctx.setVariable("nombre", nombre);
            ctx.setVariable("token", token);
            ctx.setVariable("lenguaje", locale.getLanguage());

            final MimeMessage mimeMessage = mailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
            message.setSubject(messageSource.getMessage("reset.email.titulo", null, locale));
            message.setFrom("no-responder@scoutsfev.org", "Cudú");
            message.setTo(email);

            final String htmlContent = templateEngine.process("resetpassword", ctx);
            message.setText(htmlContent, true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            String correlationId = UUID.randomUUID().toString();
            Marker marker = MarkerFactory.getMarker("ENVIO_EMAIL");
            logger.error(marker, "Error enviando email. Token: " + token + ", CorrelationId: " + correlationId,  e);
            eventPublisher.publishEvent(new EmailErrorApplicationEvent(email, correlationId));
        }
    }
}
