package com.quizter.dto.test;

import com.quizter.dictionary.QuestionType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionDto {
    Long id;

    @NotNull(message = "Question name can't be empty")
    String name;

    Map<String, Boolean> answers;

    String unitTest;

    String codeTask;

    @NotNull(message = "Question mark doesn't set")
    Double price;

    boolean reviewed;

    QuestionType questionType;

}
