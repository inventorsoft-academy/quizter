package com.quizter.repository;

import com.quizter.entity.test.ResultAnswer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResultAnswerRepository extends CrudRepository<ResultAnswer, Long> {

    Optional<ResultAnswer> findByQuizResultIdAndQuestionId(String quizResultId, Long questionId);
}
