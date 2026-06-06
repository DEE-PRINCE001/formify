package com.honour.formify.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.honour.formify.service.Interface.EmailService;

import io.mailtrap.client.MailtrapClient;
import io.mailtrap.config.MailtrapConfig;
import io.mailtrap.factory.MailtrapClientFactory;
import io.mailtrap.model.request.emails.Address;
import io.mailtrap.model.request.emails.MailtrapMail;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MailTrapEmailService implements EmailService{
    // private final JavaMailSender mailSender;
    
    
    @Value("${MAIL_FROM_ADDRESS:noreply@yourdomain.com}")
    private String fromAddress;

    @Value("${MAIL_API_KEY}")
    private String TOKEN;
    
    public void sendPasswordResetEmail(String to, String token) {
        
            String resetUrl = "http://localhost:5173/reset-password?token=" + token;
            final MailtrapConfig config = new MailtrapConfig.Builder()
                .token(TOKEN)
                .build();
    
            final MailtrapClient client = MailtrapClientFactory.createMailtrapClient(config);
    
            final MailtrapMail mail = MailtrapMail.builder()
                .from(new Address(fromAddress, "Formify"))
                .to(List.of(new Address(to)))
                .subject("Formify Account Password Reset")
                .text("To reset your password, click the link below:\n\n" + resetUrl + 
                    "\n\nThis link will expire in 15 minutes.")
                .category("Integration Test")
                .build();
    
            try {
                System.out.println(client.send(mail));
            } catch (Exception e) {
                System.out.println("Caught exception : " + e);
            }
        }
    }


    //     System.out.println("Mailer: It got her but haven't sent");

    //     SimpleMailMessage message = new SimpleMailMessage();
    //     message.setFrom(fromAddress);
    //     message.setTo(to);
    //     message.setSubject("Password Reset Request - Form Builder");
    //     message.setText("To reset your password, click the link below:\n\n" + resetUrl + 
    //                     "\n\nThis link will expire in 15 minutes.");
        
    //     mailSender.send(message);
    // }

