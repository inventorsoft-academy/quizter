package com.quizter.dto.test;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestDto<D extends QuestionDto> {

	Long id;

	String name;

	String subject;

	String description;

	List<D> questions;
}
