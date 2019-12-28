package com.quizter.mapper.test;

import com.quizter.dto.test.QuizResultDto;
import com.quizter.dto.test.QuizResultInfoDto;
import com.quizter.entity.test.Question;
import com.quizter.entity.test.QuizResult;
import com.quizter.entity.test.ResultAnswer;
import com.quizter.repository.QuestionRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResultMapper {

    QuestionRepository questionRepository;

    public ResultAnswer quizResultDtoToAnswer(QuizResultDto quizResultDto) {
        ResultAnswer resultAnswer = new ResultAnswer();
        resultAnswer.setQuestion((Question) questionRepository.findById(quizResultDto.getQuestionId()).orElseThrow());
        resultAnswer.setStringAnswers(quizResultDto.getAnswers());
        return resultAnswer;
    }

    public QuizResultInfoDto toQuizResultInfoDto(QuizResult quizResult) {
        QuizResultInfoDto quizResultInfoDto = new QuizResultInfoDto();
        quizResultInfoDto.setId(quizResult.getId());
        quizResultInfoDto.setTestName(quizResult.getTest().getName());
        quizResultInfoDto.setSubject(quizResult.getTest().getSubject());
        quizResultInfoDto.setDescription(quizResult.getTest().getDescription());
        quizResultInfoDto.setPassed(DateTimeFormatter.ofPattern("dd MM yyyy, HH:mm:ss")
                .withZone(ZoneId.systemDefault()).format(quizResult.getFinished()));
        quizResultInfoDto.setRating(new DecimalFormat("##.##").format(quizResult.getTotalRating()));
        return quizResultInfoDto;
    }
}
