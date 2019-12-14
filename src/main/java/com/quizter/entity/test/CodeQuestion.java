package com.quizter.entity.test;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("coding")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CodeQuestion extends AbstractQuestion {

	String unitTest;

	String codeTask;

	boolean reviewed;
}
