package com.honour.formify.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.honour.formify.service.Interface.EmailService;
import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;

@Service
@RequiredArgsConstructor
public class ResendEmailService implements EmailService {

    @Value("${MAIL_API_KEY}")
    private String TOKEN;
    
    public void sendPasswordResetEmail(String to, String token) throws ResendException {
        
            String resetUrl = "http://formify-navy-pi.vercel.app/reset-password?token=" + token;
            String htmlContent = String.format(
    "<p>To reset your password,</p><p>Click <a href='%s'>here</a> <br></p><p>The link will expire in 15 minutes.</p>",
    resetUrl);
            
        Resend resend = new Resend(TOKEN);

        CreateEmailOptions createEmailOptions = CreateEmailOptions.builder()
                            .from("onboarding@resend.dev")
                            .to("ajanihonour@gmail.com")
                            .subject("Formify Account Password Reset")
                            .html(htmlContent)
                            .build();

        resend.emails().send(createEmailOptions);
            }
        }