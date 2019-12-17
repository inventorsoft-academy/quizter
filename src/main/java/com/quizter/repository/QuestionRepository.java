package com.quizter.repository;

import com.quizter.entity.test.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository<T extends Question> extends JpaRepository<T, Long> {

}

