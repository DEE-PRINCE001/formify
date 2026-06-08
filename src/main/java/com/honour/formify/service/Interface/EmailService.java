package com.honour.formify.service.Interface;

import com.resend.core.exception.ResendException;

public interface EmailService{

    public void sendPasswordResetEmail(String to, String token) throws ResendException;
    
}