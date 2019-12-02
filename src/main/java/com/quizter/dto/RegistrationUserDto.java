package com.quizter.dto;

import com.quizter.annotation.PasswordMatches;
import com.quizter.dictionary.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
public class RegistrationUserDto {

    @NotNull
    @Email
    String email;

    @NotNull
    @PasswordMatches
    PasswordDto password;

    @NotNull
    String confirmPassword;

    @NotNull
    Role role;
}
