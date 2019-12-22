package com.quizter.dto.test;

import com.quizter.dictionary.QuestionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizResultDto {

    QuestionType questionType;

    Long questionId;

    List<String> answers;

}
