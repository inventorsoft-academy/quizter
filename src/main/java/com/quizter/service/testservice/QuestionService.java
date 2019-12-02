package com.quizter.service.testservice;

import com.quizter.entity.test.Question;
import com.quizter.repository.QuestionRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class QuestionService {

    QuestionRepository questionRepository;

    @Transactional
    public Set<Question> createQuestions(Set<Question> questions)
    {
        Set<Question> savedQuestions = new HashSet<>();

        for (Question e: questions) {
            Question question = new Question();
            question.setName(e.getName());
            question.setAnswers(e.getAnswers());

            questionRepository.save(question);

            savedQuestions.add(question);
        }

        return savedQuestions;
    }



}
