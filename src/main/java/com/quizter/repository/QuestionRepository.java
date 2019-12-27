package com.quizter.repository;

import com.quizter.entity.test.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository<T extends Question> extends CrudRepository<T, Long> {

}

