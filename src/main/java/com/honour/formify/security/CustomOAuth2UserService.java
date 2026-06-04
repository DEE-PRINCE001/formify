package com.honour.formify.security;

import com.honour.formify.enums.AuthProvider;
import com.honour.formify.entity.Role;
import com.honour.formify.entity.User;
import com.honour.formify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // Find out which provider this is (google or github)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        
        // Extract the email
        String email = "";
        if (registrationId.equalsIgnoreCase("google")) {
            email = oAuth2User.getAttribute("email");
        } else if (registrationId.equalsIgnoreCase("github")) {
            // GitHub sometimes hides the email or puts it in a different attribute, 
            // but usually 'email' or 'login' works for basic setup.
            email = oAuth2User.getAttribute("email");
            if (email == null) {
                email = oAuth2User.getAttribute("login") + "@github.com"; // Fallback
            }
        }

        // Check if user exists in our DB
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user;

        if (userOptional.isPresent()) {
            user = userOptional.get();
            // If they previously registered locally with a password, but now use Google, 
            // update their provider to Google (or just leave it, depending on your business logic).
            if (!user.getProvider().name().equalsIgnoreCase(registrationId)) {
                user.setProvider(AuthProvider.valueOf(registrationId.toUpperCase()));
                userRepository.save(user);
            }
        } else {
            // Register a brand new user
            user = User.builder()
                    .email(email)
                    .provider(AuthProvider.valueOf(registrationId.toUpperCase()))
                    .providerId(oAuth2User.getName()) // Unique ID from Google/GitHub
                    .role(Role.USER)
                    .build();
            userRepository.save(user);
        }

        return oAuth2User; // Return the Spring Security user object
    }
}