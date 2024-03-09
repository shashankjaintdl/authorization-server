package com.commerxo.authserver.authserver.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender sender;

    public void send(String To,
                     String body,
                     String subject){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("jainshashank562@gmail.com");
        message.setTo(To);
        message.setSubject(subject);
        message.setText(body);
        this.sender.send(message);
    }
}
