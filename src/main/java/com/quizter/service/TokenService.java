package com.quizter.service;

import com.quizter.dictionary.CacheType;
import com.quizter.entity.Token;
import com.quizter.entity.User;
import com.quizter.repository.TokenRepository;
import com.quizter.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Transactional
public class TokenService {

    TokenRepository tokenRepository;
    UserRepository userRepository;

    public Token generateToken(String email, CacheType cacheType) {

        Token token = new Token();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(userRepository.findByEmail(email).orElseThrow());
        token.setType(cacheType);
        if (cacheType == CacheType.ACTIVATION) {
            token.setExpiryDate(Instant.now().plus(Duration.ofHours(4)));
        } else if (cacheType == CacheType.RECOVERY) {
            token.setExpiryDate(Instant.now().plus(Duration.ofHours(1)));
        }
        tokenRepository.save(token);
        return token;
    }

    public Token getToken(String email, CacheType type) {
        return tokenRepository.findByTypeAndAndUser(type, userRepository.findByEmail(email).orElseThrow());
    }


    public void removeToken(String email, CacheType type) {
        tokenRepository.delete(getToken(email, type));
    }

    @Scheduled(cron = "0 0 * ? * *")
    public void removeExpiredTokens() {
        tokenRepository.deleteAll(tokenRepository.findTokenByExpiryDateAfter(Instant.now()));
    }
}
