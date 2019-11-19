package com.quizter.mapper;

import com.quizter.dto.RegistrationUserDto;
import com.quizter.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private ModelMapper mapper = new ModelMapper();

    public User toUser(RegistrationUserDto registrationUserDto) {
        return mapper.map(registrationUserDto, User.class);
    }

    public RegistrationUserDto toRegistrationUserDto(User user) {
        return mapper.map(user, RegistrationUserDto.class);
    }
}
