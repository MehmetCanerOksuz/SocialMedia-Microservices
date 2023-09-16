package com.socialmedia.service;


import com.socialmedia.rabbitmq.model.MailModel;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    public void sendMail(MailModel mailModel){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("${mail}");
        mailMessage.setTo(mailModel.getEmail());
        mailMessage.setSubject("AKTIVASYON KODUNUZ ve TOKEN...");
        mailMessage.setText("Merhaba "+ mailModel.getUsername()+"\n" + "Token =>>" + mailModel.getToken()+"\n"+
                "Aktivasyon Kodu ==>> " + mailModel.getActivationCode());
        javaMailSender.send(mailMessage);
        System.out.println("Mail gönderildi..");
    }
}
