package com.quizter.mapper.test;

import com.quizter.dto.test.QuizResultDto;
import com.quizter.entity.test.Question;
import com.quizter.entity.test.ResultAnswer;
import com.quizter.repository.QuestionRepository;
import com.quizter.repository.QuizResultRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResultMapper {

    QuestionRepository questionRepository;

    public ResultAnswer quizResultDtoToAnswer(QuizResultDto quizResultDto){
        ResultAnswer resultAnswer = new ResultAnswer();
        resultAnswer.setQuestion((Question) questionRepository.findById(quizResultDto.getQuestionId()).orElseThrow());
        resultAnswer.setStringAnswers(quizResultDto.getAnswers());
        return resultAnswer;
    }
}
