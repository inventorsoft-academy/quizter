package com.quizter.dto.test;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizResultInfoDto {

    String id;
    String testName;
    String subject;
    String description;
    String passed;
    String rating;

}
