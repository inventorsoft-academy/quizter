package com.quizter.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEmailDto {

    @Email
    String userEmail;

}
