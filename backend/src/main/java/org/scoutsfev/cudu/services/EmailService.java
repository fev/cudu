package org.scoutsfev.cudu.services;

import javax.mail.MessagingException;
import java.util.Locale;

public interface EmailService {
    void enviarMailCambioContraseña(String nombre, String email, String token, Locale locale) throws MessagingException;
}
