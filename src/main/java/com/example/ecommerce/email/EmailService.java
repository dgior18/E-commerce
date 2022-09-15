package com.example.ecommerce.email;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;

import org.springframework.mail.javamail.MimeMessageHelper;

@Service
@AllArgsConstructor
public class EmailService{


    public void send(String recipient, String text) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        final String myAccountEmail = "dgior18@freeuni.edu.ge";
        final String password = "60001147346";
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });
        Message message = prepareMessage(session, myAccountEmail, recipient, text);
        Transport.send(message);
    }

    private static Message prepareMessage(Session session, String myAccountMail, String recipient, String text) {
        try {
            MimeMessage message = new MimeMessage(session);
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, "utf-8");
            helper.setText(text, true);
            helper.setTo(recipient);
            helper.setSubject("Confirm your email");
            helper.setFrom(myAccountMail);
            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
