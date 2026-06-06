package com.honour.formify.service.Interface;

public interface EmailService {

    public void sendPasswordResetEmail(String to, String token);
    
}