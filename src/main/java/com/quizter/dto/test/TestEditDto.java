package com.quizter.dto.test;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestEditDto {

    Long id;

    @NotNull(message = "Description can't be empty")
    @Size(min = 10, message = "Description length should have at least 10 characters")
    String description;

    @NotNull(message = "Test duration doesn't set")
    @Range(min = 5, max = 180, message = "Test duration must be in range from 5 to 180 minutes")
    Integer duration;

    List<QuestionDto> questions;
}
