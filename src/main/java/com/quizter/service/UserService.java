package com.quizter.service;

import com.quizter.dictionary.Role;
import com.quizter.dto.RegistrationUserDto;
import com.quizter.entity.User;
import com.quizter.mapper.UserMapper;
import com.quizter.repository.UserRepository;
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

    MailWebService mailWebService;

    UserMapper userMapper;

    PasswordEncoder passwordEncoder;

    public void registerUser(RegistrationUserDto registrationUserDto) {
        User user = userMapper.toUser(registrationUserDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(false);
        mailWebService.registrationMailSend(user.getEmail());
        userRepository.save(user);
    }
}
