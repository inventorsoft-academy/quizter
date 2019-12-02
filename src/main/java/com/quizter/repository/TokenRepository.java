package com.quizter.repository;

import com.quizter.dictionary.CacheType;
import com.quizter.entity.Token;
import com.quizter.entity.User;

import java.time.Instant;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends CrudRepository<Token, Long> {

    Token findByToken(String token);

    Token findByTypeAndAndUser(User user, CacheType type);

    List<Token> findTokenByExpiryDateAfter(Instant now);

    void deleteByUserId(Long id);
}
