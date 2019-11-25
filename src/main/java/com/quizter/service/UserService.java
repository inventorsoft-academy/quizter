package com.quizter.service;

import com.quizter.dto.RegistrationUserDto;
import com.quizter.entity.PasswordResetToken;
import com.quizter.entity.User;
import com.quizter.exception.PasswordConfirmException;
import com.quizter.mapper.UserMapper;
import com.quizter.repository.PasswordRepository;
import com.quizter.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserService {

    UserRepository userRepository;

    UserMapper userMapper;

    PasswordEncoder passwordEncoder;

    PasswordRepository passwordRepository;

    public void registerUser(RegistrationUserDto registrationUserDto) {
        if (registrationUserDto.isConfirmed()) {
            User user = userMapper.toUser(registrationUserDto);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        } else throw new PasswordConfirmException("Password wasn't confirmed");
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setUser(user);
        passwordResetToken.setToken(token);
        passwordResetToken.setExpiryDate(Instant.ofEpochSecond(Instant.now().getEpochSecond() + 3600));
        passwordRepository.save(passwordResetToken);
    }

    public void saveNewPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        passwordRepository.deleteByUserId(user.getId());
        userRepository.save(user);
    }
}
