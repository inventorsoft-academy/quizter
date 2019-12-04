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
        return passwordResetToken.filter(
                token1 -> !token1.getUser().getId().equals(id)
                        && token1.getExpiryDate().isAfter(Instant.now()))
                .isPresent();
    }
}
