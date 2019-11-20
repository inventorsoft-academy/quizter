package com.quizter.mapper;

import com.quizter.dto.RegistrationUserDto;
import com.quizter.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserMapper {

    ModelMapper mapper;

    public User toUser(RegistrationUserDto registrationUserDto) {
        return mapper.map(registrationUserDto, User.class);
    }

    public RegistrationUserDto toRegistrationUserDto(User user) {
        return mapper.map(user, RegistrationUserDto.class);
    }
}
