package com.honour.formify.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    // Optional but professional: Pull the "from" address from your environment variables too
    @Value("${MAIL_FROM_ADDRESS:noreply@yourdomain.com}")
    private String fromAddress;

    public void sendPasswordResetEmail(String to, String token) {
        String resetUrl = "http://localhost:5173/reset-password?token=" + token;
        System.out.println("Mailer: It got her but haven't sent");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(to);
        message.setSubject("Password Reset Request - Form Builder");
        message.setText("To reset your password, click the link below:\n\n" + resetUrl + 
                        "\n\nThis link will expire in 15 minutes.");
        
        mailSender.send(message);
    }
}