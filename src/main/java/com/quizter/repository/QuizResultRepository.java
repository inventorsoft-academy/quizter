package com.quizter.repository;

import com.quizter.entity.User;
import com.quizter.entity.test.QuizResult;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizResultRepository extends CrudRepository<QuizResult, String> {

    @EntityGraph(attributePaths = "applicant")
    Optional<QuizResult> findByTestName(String testName);

    List<QuizResult> findAllByApplicantAndIsCompleted(User applicant, Boolean complited);
}
