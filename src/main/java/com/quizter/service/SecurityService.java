package com.quizter.service;

import com.quizter.entity.Token;
import com.quizter.repository.TokenRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Transactional
@AllArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SecurityService {

    TokenRepository tokenRepository;

    public boolean validateResetToken(Long id, String token) {
        Optional<Token> passwordResetToken = tokenRepository.findByToken(token);
        if (passwordResetToken.isEmpty() || passwordResetToken.get().getUser().getId() != id ||
                (passwordResetToken.get().getExpiryDate().getEpochSecond() - Instant.now().getEpochSecond() < 0)) {
            return false;
        }
        return true;
    }
}
