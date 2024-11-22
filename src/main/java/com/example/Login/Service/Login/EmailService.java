package com.example.Login.Service.Login;

import com.example.Login.Dto.MailBody;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendSimpleMsg(MailBody mailBody){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailBody.to());
        message.setFrom("dinujapraneeth76@gmail.com");
        message.setSubject(mailBody.subject());
        message.setText(mailBody.text());

        try{
            javaMailSender.send(message);
        }catch (MailSendException e){
            System.out.println("Failed to send email: " + e.getMessage());
        }catch (Exception e){
            System.out.println("An error occurred while sending the email: " + e.getMessage());
        }

    }
}
