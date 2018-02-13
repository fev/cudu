package org.scoutsfev.cudu.services;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

public interface EmailService {
    void enviarMailCambioContraseña(String nombre, String email, String token, Locale locale);

    void enviarMailNuevaApikey(String nombre, String email, String token, Locale locale);

}
