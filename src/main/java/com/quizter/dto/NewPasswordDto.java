package com.quizter.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewPasswordDto {

    @NotNull
    String password;

    @NotNull
    String confirmPassword;
}
