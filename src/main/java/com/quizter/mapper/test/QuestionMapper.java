package com.quizter.mapper.test;

import com.quizter.dictionary.QuestionType;
import com.quizter.dto.test.QuestionDto;
import com.quizter.entity.test.CodeQuestion;
import com.quizter.entity.test.MultiVariantQuestion;
import com.quizter.entity.test.Question;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionMapper {

    ModelMapper mapper = new ModelMapper();

    public MultiVariantQuestion questionDtoToMultivariantQuestion(QuestionDto questionDto) {
        return mapper.map(questionDto, MultiVariantQuestion.class);
    }

    public CodeQuestion questionDtoToCodeQuestion(QuestionDto questionDto) {
        return mapper.map(questionDto, CodeQuestion.class);
    }

    public <T extends Question> QuestionDto questionEntityToQuestionDto(T question) {
        QuestionDto questionDto = mapper.map(question, QuestionDto.class);
        if (question instanceof CodeQuestion) {
            questionDto.setQuestionType(QuestionType.CODE);
            return questionDto;
        }
        if (question instanceof MultiVariantQuestion) {
            questionDto.setQuestionType(QuestionType.MULTIVARIANT);
            return questionDto;
        }
        return questionDto;
    }
}
