package com.quizter.dto;

import com.quizter.annotation.PasswordMatches;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@PasswordMatches
public class PasswordDto {

    @NotNull(message = "Password can't be empty")
    String password;

    @NotNull(message = "Please, confirm password")
    String confirmPassword;
}
