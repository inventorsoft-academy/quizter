package com.quizter.mapper.test;

import com.quizter.dto.test.QuestionDto;
import com.quizter.dto.test.TestDto;
import com.quizter.entity.test.Question;
import com.quizter.entity.test.Test;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestMapper {

    ModelMapper mapper = new ModelMapper();

    public Test toTest(TestDto testDto) {
        return mapper.map(testDto, Test.class);
    }

    public TestDto toTestDto(Test test) {
        return mapper.map(test, TestDto.class);
    }

    public List<TestDto> toTestListDto(List<Test> tests) {
        Type targetListType = new TypeToken<List<TestDto>>() {
        }.getType();

        return mapper.map(tests, targetListType);
    }

    public List<Question> toQuestionList(List<QuestionDto> questionDtos) {
        Type targetListType = new TypeToken<List<Question>>() {
        }.getType();

        return mapper.map(questionDtos, targetListType);
    }

}
