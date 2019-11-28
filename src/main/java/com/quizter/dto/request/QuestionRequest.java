package com.quizter.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionRequest {

    String name;

    Map<String, Boolean> answers;

}
