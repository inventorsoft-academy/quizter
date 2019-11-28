package com.quizter.service;

import com.quizter.entity.Token;
import com.quizter.entity.User;
import com.quizter.repository.TokenRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.logging.Logger;

@Transactional
@AllArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SecurityService {

    static final Logger LOG = Logger.getLogger(SecurityService.class.getName());

    TokenRepository tokenRepository;

    public boolean validateResetToken(Long id, String token) {
        Token passwordResetToken = tokenRepository.findByToken(token);
        if (passwordResetToken == null || passwordResetToken.getUser().getId() != id ||
                (passwordResetToken.getExpiryDate().getEpochSecond() - Instant.now().getEpochSecond() < 0)) {
            return false;
        }
        return true;
    }
}
