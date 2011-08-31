 package org.scoutsfev.cudu.services;
import javax.mail.MessagingException;

 public interface EmailService   {

    public void enviarEmailA(String emailAsociado) throws Exception;
    public void enviarMensajeSSL(String recipients[], String subject,
        String message, String from) throws MessagingException;
    public void enviarEmailA(String emailAsociado,String subject,String msg) throws Exception;
}