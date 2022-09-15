package com.example.ecommerce.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@Slf4j
public class EmailServiceConfiguration {


    private JavaMailSender javaMailSender;

    @Bean
    public JavaMailSender returnEmail(){
        javaMailSender = new JavaMailSenderImpl();
        return javaMailSender;
    }

}
