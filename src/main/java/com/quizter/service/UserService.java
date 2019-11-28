package com.quizter.service;

import com.quizter.dto.RegistrationUserDto;
import com.quizter.dto.UserEmailDto;
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
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserService {

    UserRepository userRepository;

    UserMapper userMapper;

    PasswordEncoder passwordEncoder;

    PasswordRepository passwordRepository;

    MailWebService mailWebService;

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

    public void saveNewPassword(Long id, String password) {
        User user = userRepository.findById(id).get();
        user.setPassword(passwordEncoder.encode(password));
        passwordRepository.deleteByUserId(user.getId());
        userRepository.save(user);
    }

    public void resetPassword(UserEmailDto userEmailDto) {
        String email = userEmailDto.getUserEmail();
        Optional<User> userOptional = findUserByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String token = UUID.randomUUID().toString();
            createPasswordResetTokenForUser(user, token);
            //TODO http://localhost:8080 move in app prop
            String appUrl = "http://localhost:8080/newPassword?id=" + user.getId() + "&token=" + token;
            // TODO async
            mailWebService.mailSend(user.getEmail(), "Restore password",
                    "reset-password-content", appUrl);
        }
    }
}
