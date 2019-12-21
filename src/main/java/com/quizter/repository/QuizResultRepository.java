package com.quizter.repository;

import com.quizter.entity.test.QuizResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuizResultRepository extends CrudRepository<QuizResult, Long> {

    Optional<QuizResult> findByTestName(String testName);
}
