package com.socialmedia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootApplication
public class MailServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MailServiceApplication.class);
    }

    //deneme amaçlı kod

//    @Autowired
//    private JavaMailSender javaMailSender;
//    @EventListener(ApplicationReadyEvent.class)
//    public void sendMail(){
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setFrom("${mail}");
//        mailMessage.setTo("java9and10@gmail.com");
//        mailMessage.setSubject("AKTIVASYON KODUNUZ...");
//        mailMessage.setText("kjSR43");
//        javaMailSender.send(mailMessage);
//    }
}