package com.quizter.repository;

import com.quizter.dictionary.CacheType;
import com.quizter.entity.Token;
import com.quizter.entity.User;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends CrudRepository<Token, Long> {

    Optional<Token> findByToken(String token);

    Optional<Token> findByTypeAndUser(CacheType type, User user);

    List<Token> findTokenByExpiryDateAfter(Instant now);

}
