package com.quizter.dto;

import com.quizter.dictionary.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class RegistrationUserDto {

    @NotNull
    String email;

    @NotNull
    String password;

    @NotBlank
    String confirmPassword;

    @NotBlank
    Role role;

    public Boolean isConfirmed() {
        return this.confirmPassword.equals(this.password);
    }
}
