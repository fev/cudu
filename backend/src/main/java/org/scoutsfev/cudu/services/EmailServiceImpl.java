package org.scoutsfev.cudu.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Service
@Profile("production")
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void enviarMailCambioContraseña(String nombre, String email, String token, Locale locale) throws MessagingException {
        final Context ctx = new Context(locale);
        ctx.setVariable("nombre", nombre);
        ctx.setVariable("token", token);

        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject("Cudu: Cambio de Contraseña");
        message.setFrom("no-responder@scoutsfev.org");
        message.setTo(email);

        final String htmlContent = templateEngine.process("resetpassword", ctx);
        message.setText(htmlContent, true);
        mailSender.send(mimeMessage);
    }
}
