package com.quizter.dto.test;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestDto {

    Long id;

    String name;

    String subject;

    String description;

    Integer duration;

    String version;

    List<QuestionDto> questions;
}
