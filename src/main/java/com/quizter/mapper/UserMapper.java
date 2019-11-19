package com.quizter.mapper;

import com.quizter.dto.RegistrationUserDto;
import com.quizter.entity.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {

    private final ModelMapper mapper;

    public User toUser(RegistrationUserDto registrationUserDto) {
        return mapper.map(registrationUserDto, User.class);
    }

    public RegistrationUserDto toRegistrationUserDto(User user) {
        return mapper.map(user, RegistrationUserDto.class);
    }
}
