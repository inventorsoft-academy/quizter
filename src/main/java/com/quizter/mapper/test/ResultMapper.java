package com.quizter.mapper.test;

import com.quizter.dto.test.QuizResultDeskAvailableDto;
import com.quizter.dto.test.QuizResultDeskPassedDto;
import com.quizter.dto.test.QuizResultDto;
import com.quizter.entity.test.QuizResult;
import com.quizter.entity.test.ResultAnswer;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResultMapper {

    ModelMapper modelMapper = new ModelMapper();

    public ResultAnswer toResultAnswer(QuizResultDto quizResultDto) {
        return modelMapper.map(quizResultDto, ResultAnswer.class);
    }

    public QuizResultDeskAvailableDto toQuizResultDeskAvailableDto(QuizResult quizResult) {
        return modelMapper.map(quizResult, QuizResultDeskAvailableDto.class);
    }

    public QuizResultDeskPassedDto toQuizResultDeskPassedDto(QuizResult quizResult) {
        return modelMapper.map(quizResult, QuizResultDeskPassedDto.class);
    }

}
