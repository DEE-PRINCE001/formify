package com.honour.formify.service;

import com.honour.formify.dtos.ForgotPasswordRequest;
import com.honour.formify.dtos.ResetPasswordRequest;
import com.honour.formify.entity.PasswordResetToken;
import com.honour.formify.entity.User;
import com.honour.formify.repository.PasswordResetTokenRepository;
import com.honour.formify.repository.UserRepository;
import com.honour.formify.service.ResendEmailService;
import com.resend.core.exception.ResendException;
import com.honour.formify.service.MailTrapEmailService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    //private final MailTrapEmailService mailTrapEmailService;
    // private final GmailSMTPService gmailSMTPService;
    private final ResendEmailService resendEmailService;
    
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void requestPasswordReset(ForgotPasswordRequest request) throws ResendException {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        // If user exists, generate token and send email. 
        // If not, silently ignore to prevent email enumeration.
        if (userOptional.isPresent()) {
            User user = userOptional.get();

       
            tokenRepository.deleteByUser_Id(user.getId());

            tokenRepository.flush();

            // random UUID token
            String token = UUID.randomUUID().toString();

            PasswordResetToken resetToken = PasswordResetToken.builder()
                    .token(token)
                    .user(user)
                    .expiryDate(LocalDateTime.now().plusMinutes(15)) // 15-minute lifespan
                    .build();

            tokenRepository.save(resetToken);

           resendEmailService.sendPasswordResetEmail(user.getEmail(), token);
            // mailTrapEmailService.sendPasswordResetEmail(user.getEmail(), token);
        }
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        PasswordResetToken resetToken = tokenRepository.findByToken(request.getToken())
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (resetToken.isExpired()) {
            tokenRepository.delete(resetToken);
            throw new RuntimeException("Token has expired");
        }

        User user = resetToken.getUser();
        
        // Hash the new password and save
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        // Delete the token so it can never be used again
        tokenRepository.delete(resetToken);
    }
}