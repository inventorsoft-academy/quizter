package com.quizter.repository;

import com.quizter.entity.PasswordResetToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordRepository extends CrudRepository<PasswordResetToken, Long> {

    PasswordResetToken save(PasswordResetToken passwordResetToken);

    String findTokenById(Long id);

    PasswordResetToken findByToken(String token);
}
