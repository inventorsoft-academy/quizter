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
public class PasswordDto {

    @NotNull
    String password;

    @NotNull
    @PasswordMatches
    String confirmPassword;
}
