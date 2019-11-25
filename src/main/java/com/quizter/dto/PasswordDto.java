package com.quizter.dto;

import com.quizter.annotation.PasswordMatches;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordDto {
    String userPassword;

    @PasswordMatches
    String confirmPassword;
}
