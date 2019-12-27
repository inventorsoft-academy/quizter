package com.quizter.dto;

import com.quizter.entity.Profile;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentDto {

    Long id;

    Profile profile;

    String email;

    boolean active;
}

