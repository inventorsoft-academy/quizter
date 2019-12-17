package com.quizter.dto.test;

import com.quizter.dictionary.QuestionType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionDto {
	Long id;

	String name;

	Map<String, Boolean> answers;

	String unitTest;

	String codeTask;

	boolean reviewed;

	QuestionType questionType;

}
