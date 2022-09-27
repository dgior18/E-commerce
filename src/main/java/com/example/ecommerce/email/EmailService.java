package com.example.ecommerce.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
@Slf4j
public class EmailService {

    @Value("${application.email}")
    private String email;

    @Value("${application.password}")
    private String password;

    public void send(String recipient, String text, String subject) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });
        Message message = prepareMessage(session, email, recipient, text, subject);

        log.info("Set credentials to send and build email.");

        Transport.send(message);
    }

    private static Message prepareMessage(Session session, String myAccountMail, String recipient, String text, String subject) {
        try {
            MimeMessage message = new MimeMessage(session);
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, "utf-8");
            helper.setText(text, true);
            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setFrom(myAccountMail);
            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
