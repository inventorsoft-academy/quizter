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
public class QuizResultDeskPassedDto {

    Long testId;
    String testName;
    String testSubjectName;
    String testDescription;
    String passed;
    String rating;

}
