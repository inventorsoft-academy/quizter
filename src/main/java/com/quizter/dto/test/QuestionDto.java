package com.quizter.dto.test;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionDto {

    Long id;

    String name;

    Map<String, Boolean> answers;

}
