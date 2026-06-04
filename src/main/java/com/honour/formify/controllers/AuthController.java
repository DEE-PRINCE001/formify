package com.honour.formify.controllers;

import com.honour.formify.dtos.AuthenticationRequest;
import com.honour.formify.dtos.AuthenticationResponse;
import com.honour.formify.dtos.RegisterRequest;
import com.honour.formify.dtos.ForgotPasswordRequest;
import com.honour.formify.dtos.Message;
import com.honour.formify.dtos.ResetPasswordRequest;
import com.honour.formify.service.AuthenticationService;
import com.honour.formify.service.PasswordResetService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    private final PasswordResetService passwordResetService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Message> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        passwordResetService.requestPasswordReset(request);
        // Always return success to obscure whether the email exists or not
        return ResponseEntity.ok(new Message("If an account with that email exists, a reset link has been sent."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Message> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        passwordResetService.resetPassword(request);
        return ResponseEntity.ok(new Message("Password reset is successful"));
    }
}