package com.quizte.service;

import com.quizte.dto.RegistrationUserDto;
import com.quizte.entity.User;
import com.quizte.exception.PasswordConfirmException;
import com.quizte.mapper.UserMapper;
import com.quizte.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserService {

    UserRepository userRepository;

    UserMapper userMapper;

    PasswordEncoder passwordEncoder;

    public void registerUser(RegistrationUserDto registrationUserDto) {
        if (registrationUserDto.isConfirmed()) {
            User user = userMapper.toUser(registrationUserDto);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        } else throw new PasswordConfirmException("Password wasn't confirmed");
    }
}
