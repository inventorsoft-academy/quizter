package com.quizter.dto;

import com.quizter.annotation.PasswordMatches;
import com.quizter.dictionary.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
@PasswordMatches
public class RegistrationUserDto {

    @NotNull
    @Email
    String email;

    @NotNull
    String password;

    @NotNull
    String confirmPassword;

    @NotNull
    Role role;
}
