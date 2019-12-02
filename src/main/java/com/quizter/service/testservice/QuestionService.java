package com.quizter.service.testservice;

import com.quizter.entity.test.Question;
import com.quizter.repository.QuestionRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class QuestionService {

    QuestionRepository questionRepository;

    @Transactional
    public List<Question> createQuestions(List<Question> questions)
    {
        List<Question> savedQuestions = new ArrayList<>();

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
