 package org.scoutsfev.cudu.services;
import javax.mail.MessagingException;

//    @Autowired
//    protected String sever;


public interface EmailService   {

    public void enviarEmailA(String emailAsociado) throws Exception;
    public void enviarMensajeSSL(String recipients[], String subject,
        String message, String from) throws MessagingException;
}