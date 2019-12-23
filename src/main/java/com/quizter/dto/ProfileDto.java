package com.quizter.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileDto {

    String firstName;

    String lastName;

    String sphere;

    String phoneNumber;

    MultipartFile file;
}
