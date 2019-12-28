package com.quizter.repository;

import com.quizter.entity.User;
import com.quizter.entity.test.QuizResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizResultRepository extends CrudRepository<QuizResult, String> {

    List<QuizResult> findAllByApplicantAndIsCompleted(User applicant, Boolean completed);

    Optional<QuizResult> findQuizResultByTestId(Long id);

    Optional<QuizResult> findByApplicantAndTestId(User userPrincipal, Long id);

}
