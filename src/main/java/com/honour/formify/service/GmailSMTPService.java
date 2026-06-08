package com.honour.formify.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.honour.formify.service.Interface.EmailService;

@Service
public class GmailSMTPService implements EmailService {
    
    private final JavaMailSender mailSender;

    
    @Value("${MAIL_FROM_ADDRESS:noreply@yourdomain.com}")
    private String fromAddress;

    public GmailSMTPService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    @Override
    public void sendPasswordResetEmail(String to, String token) {
        String resetUrl = "http://formify-navi-pi.vercel.app/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Formify Account Password Reset");
        message.setText("To reset your password, click the link below:\n\n" + resetUrl + 
                    "\n\nThis link will expire in 15 minutes.");
        message.setFrom(fromAddress);
        mailSender.send(message);
   
    }
    }