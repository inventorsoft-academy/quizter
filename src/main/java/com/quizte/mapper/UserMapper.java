package com.quizte.mapper;

import com.quizte.dto.RegistrationUserDto;
import com.quizte.entity.User;
import org.modelmapper.ModelMapper;

public class UserMapper {
    private static final ModelMapper mapper = new ModelMapper();

    private UserMapper() {
    }

    public static User toUser(RegistrationUserDto registrationUserDto) {
        return mapper.map(registrationUserDto, User.class);
    }

    public static RegistrationUserDto toRegistrationUserDto(User user){
        return mapper.map(user, RegistrationUserDto.class);
    }
}
