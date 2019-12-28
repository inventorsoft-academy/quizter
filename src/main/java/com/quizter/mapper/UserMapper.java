package com.quizter.mapper;

import com.quizter.dto.RegistrationUserDto;
import com.quizter.dto.StudentDto;
import com.quizter.entity.User;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserMapper {

    ModelMapper mapper = new ModelMapper();

    public User toUser(RegistrationUserDto registrationUserDto) {
        return mapper.map(registrationUserDto, User.class);
    }
    public User toUser(StudentDto registrationUserDto) {
        return mapper.map(registrationUserDto, User.class);
    }

    public User toUserFromStudentDto(StudentDto studentDto) {
        return mapper.map(studentDto, User.class);
    }

    public RegistrationUserDto toRegistrationUserDto(User user) {
        return mapper.map(user, RegistrationUserDto.class);
    }

}
