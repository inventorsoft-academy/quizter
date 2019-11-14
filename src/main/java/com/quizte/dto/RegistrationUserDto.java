package com.quizte.dto;

import com.quizte.dictionary.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class RegistrationUserDto {

    String email;

    String password;

    String confirmPassword;

    Set<Role> roles;

    public Boolean isConfirmed() {
        return this.confirmPassword.equals(this.password);
    }
}
