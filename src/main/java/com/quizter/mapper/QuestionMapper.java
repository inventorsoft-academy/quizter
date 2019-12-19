package com.quizter.mapper;

import com.quizter.dto.test.QuestionDto;
import com.quizter.entity.test.CodeQuestion;
import com.quizter.entity.test.MultiVariantQuestion;
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
}
