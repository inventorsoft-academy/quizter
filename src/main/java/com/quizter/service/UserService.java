package com.quizter.service;

import com.quizter.dictionary.CacheType;
import com.quizter.dto.RegistrationUserDto;
import com.quizter.dto.UserEmailDto;
import com.quizter.entity.User;
import com.quizter.exception.NoUserWithThatIDException;
import com.quizter.exception.TokenException;
import com.quizter.mapper.UserMapper;
import com.quizter.repository.UserRepository;
import com.quizter.util.AppConstants;
import com.quizter.util.EmailConstants;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService {

    UserRepository userRepository;

    MailWebService mailWebService;

    TokenService tokenService;

    UserMapper userMapper;

    ValidationService validationService;

    PasswordEncoder passwordEncoder;

    AppConstants appConstants;

    public void registerUser(RegistrationUserDto registrationUserDto) {
        validationService.registrationValidation(registrationUserDto);
        User user = userMapper.toUser(registrationUserDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(false);
        userRepository.save(user);
        mailWebService.mailSend(user.getEmail(), EmailConstants.REGISTRATION_SUBJECT, EmailConstants.MAIL_CONTENT_URL,
                appConstants.getHost() + "/active-account?id=" + user.getId() + "&token="
                        + tokenService.generateToken(user.getEmail(), CacheType.ACTIVATION).getToken());
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void saveNewPassword(Long id, String password) {
        User user = userRepository.findById(id).get();
        user.setPassword(passwordEncoder.encode(password));
        tokenService.deleteToken(id);
        userRepository.save(user);
    }

    public void activateUser(Long id, String token) {

        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            String trueToken = tokenService.getToken(user.get().getEmail(), CacheType.ACTIVATION).getToken();

            if (trueToken == null) {
                userRepository.deleteById(id);
                throw new TokenException("Authentication token expired");
            }

            if (!trueToken.equals(token)) {
                throw new TokenException("Token is wrong");
            }

            tokenService.removeToken(user.get().getEmail(), CacheType.ACTIVATION);

            user.get().setActive(true);
            userRepository.save(user.get());
        } else {
            throw new NoUserWithThatIDException("user", "id", id);
        }
    }

    public void resetPassword(UserEmailDto userEmailDto) {
        String email = userEmailDto.getUserEmail();
        Optional<User> userOptional = findUserByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String token = tokenService.generateToken(email, CacheType.RECOVERY).getToken();
            String appUrl = appConstants.getHost() + "/newPassword?id=" + user.getId() + "&token=" + token;
            log.info(appUrl);
            mailWebService.mailSend(user.getEmail(), "Restore password",
                    "reset-password-content", appUrl);
        }
    }
}
