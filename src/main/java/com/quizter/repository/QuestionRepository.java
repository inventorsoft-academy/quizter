package com.quizter.repository;

import com.quizter.entity.test.AbstractQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<AbstractQuestion, Long> {

}

