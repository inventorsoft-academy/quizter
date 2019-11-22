package com.quizter.dto.request;

import com.quizter.entity.test.Question;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestWizardRequest {

    String name;

    String subject;

    String description;

    Set<Question> questions;

}

