package com.quizter.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@ToString
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileDto {

    @NotNull(message = "Name is mandatory")
    @NotBlank(message = "Name is mandatory")
    String firstName;

    @NotNull(message = "Last name is mandatory")
    @NotBlank(message = "Last name is mandatory")
    String lastName;

    @NotNull(message = "This field is mandatory")
    @NotBlank(message = "This field is mandatory")
    String subjectName;

    @NotNull(message = "Phone number is mandatory")
    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "[\\d]{10}", message = "Phone number must be 10 digits")
    String phoneNumber;

    String photo;
}
