package org.scoutsfev.cudu.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Locale;

@Service
@Profile("dev")
public class NullEmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(NullEmailServiceImpl.class);

    @Override
    public void enviarMailCambioContraseña(String nombre, String email, String token, Locale locale) throws MessagingException {
        logger.info("Envio de email con cambio de contraseña. Usuario: {} ({}), Token: {}, Locale: {}", nombre, email, token, locale);
    }
}
