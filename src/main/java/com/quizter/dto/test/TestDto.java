package com.quizter.dto.test;

import com.quizter.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestDto {

    Long id;

    @NotNull(message = "Test name can't be empty")
    @Size(min = 5, message = "Test name length should have at least 5 characters")
    String name;

    @NotNull(message = "Subject can't be empty")
    @Size(min = 2, message = "Subject length should have at least 2 characters")
    String subject;

    @NotNull(message = "Description can't be empty")
    @Size(min = 10, message = "Description length should have at least 10 characters")
    String description;

    @NotNull(message = "Test duration doesn't set")
    @Range(min = 5, max = 180, message = "Test duration must be in range from 5 to 180 minutes")
    Integer duration;

    String version;

    User author;

    List<QuestionDto> questions;

}
