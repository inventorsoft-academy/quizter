package com.quizter.service;

import com.quizter.entity.Token;
import com.quizter.entity.User;
import com.quizter.repository.TokenRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Arrays;
import java.util.logging.Logger;

@Transactional
@AllArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SecurityService {

    static final Logger LOG = Logger.getLogger(SecurityService.class.getName());

    TokenRepository tokenRepository;

    public String validateResetToken(Long id, String token) {
        Token passwordResetToken = tokenRepository.findByToken(token);
        if (passwordResetToken == null || passwordResetToken.getUser().getId() != id) {
            return "invalidToken";
        }

        if ((passwordResetToken.getExpiryDate().getEpochSecond() - Instant.now().getEpochSecond() < 0)) {
            return "expired";
        }

        User user = passwordResetToken.getUser();
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user, null, Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "success";
    }
}
