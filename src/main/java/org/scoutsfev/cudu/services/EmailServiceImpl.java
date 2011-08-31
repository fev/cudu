
package org.scoutsfev.cudu.services;
import java.security.Security;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailServiceImpl implements EmailService{
    
    
    private static final String SMTP_HOST_NAME = "smtp.gmail.com";
    private static final String SMTP_PORT = "465";
    private static final String emailMsgTxt = "Pues si, soy tu consciencia que te da mensajitos";
    private static final String emailSubjectTxt = "No tengas miedo, soy yo!";
    private static final String emailFromAddress = "fev.lluerna@gmail.com";
    private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";


    public void enviarEmailA(String emailAsociado) throws Exception {

        String[] sendTo = { emailAsociado};
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        enviarMensajeSSL(sendTo, emailSubjectTxt,
        emailMsgTxt, emailFromAddress);
        System.out.println("Sucessfully Sent mail to All Users");
    }

    public void enviarMensajeSSL(String recipients[], String subject,
        String message, String from) throws MessagingException {
        boolean debug = true;

        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.socketFactory.port", SMTP_PORT);
        props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.put("mail.smtp.socketFactory.fallback", "false");

        Session session = Session.getDefaultInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("fev.lluerna", "fanalet79");
                }
                });
        session.setDebug(debug);
        Message msg = new MimeMessage(session);
        InternetAddress addressFrom = new InternetAddress(from);
        msg.setFrom(addressFrom);
        InternetAddress[] addressTo = new InternetAddress[recipients.length];

        for (int i = 0; i < recipients.length; i++) {
            addressTo[i] = new InternetAddress(recipients[i]);
        }
        msg.setRecipients(Message.RecipientType.TO, addressTo);
        // Setting the Subject and Content Type
        msg.setSubject(subject);
        msg.setContent(message, "text/plain");
        Transport.send(msg);
    }
    
    
    public void enviarEmailA(String emailAsociado,String subject,String msg) throws Exception {

        String[] sendTo = { emailAsociado};
        System.out.println("peto por aqui");
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        System.out.println("peto por aqui");
        enviarMensajeSSL(sendTo, subject,
        msg, emailFromAddress);
        System.out.println("Sucessfully Sent mail to All Users");
    }

}