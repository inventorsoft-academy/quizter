package com.quizter.dto.test;

import com.quizter.dictionary.QuestionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizResultDto {

    QuestionType questionType;

    Long questionId;

    List<String> answers;

}
