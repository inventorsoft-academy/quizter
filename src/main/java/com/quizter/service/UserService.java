package com.quizter.service;

import com.quizter.dto.RegistrationUserDto;
import com.quizter.entity.PasswordResetToken;
import com.quizter.entity.User;
import com.quizter.mapper.UserMapper;
import com.quizter.repository.PasswordRepository;
import com.quizter.repository.UserRepository;
import com.quizter.util.EmailConstants;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService {

    UserRepository userRepository;

    MailWebService mailWebService;

    UserMapper userMapper;

    PasswordEncoder passwordEncoder;

    PasswordRepository passwordRepository;

    public void registerUser(RegistrationUserDto registrationUserDto) {
        User user = userMapper.toUser(registrationUserDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(false);
        mailWebService.mailSend(user.getEmail(), EmailConstants.REGISTRATION_SUBJECT, EmailConstants.MAIL_CONTENT_URL);
        userRepository.save(user);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                .user(user)
                .token(token)
                .build();
        passwordRepository.save(passwordResetToken);
    }
}
