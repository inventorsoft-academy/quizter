package com.quizter.service;

import com.quizter.dictionary.CacheType;
import com.quizter.dto.RegistrationUserDto;
import com.quizter.dto.UserEmailDto;
import com.quizter.entity.Token;
import com.quizter.entity.User;
import com.quizter.exception.NoUserWithThatIDException;
import com.quizter.exception.TokenException;
import com.quizter.mapper.UserMapper;
import com.quizter.repository.TokenRepository;
import com.quizter.repository.UserRepository;
import com.quizter.util.EmailConstants;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService {

    final UserRepository userRepository;

    final MailWebService mailWebService;

    final TokenService tokenService;

    final UserMapper userMapper;

    final PasswordEncoder passwordEncoder;

    final TokenRepository tokenRepository;

    @Value("${host}")
    String domain;

    static final Logger LOG = Logger.getLogger(UserService.class.getName());

    public void registerUser(RegistrationUserDto registrationUserDto) {
        User user = userMapper.toUser(registrationUserDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(false);
        userRepository.save(user);
        mailWebService.mailSend(user.getEmail(), EmailConstants.REGISTRATION_SUBJECT, EmailConstants.MAIL_CONTENT_URL,
                domain + "/active-account?id=" + user.getId() + "&token="
                        + tokenService.generateToken(user.getEmail(), CacheType.ACTIVATION).getToken());
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void createPasswordResetTokenForUser(User user, String token) {
        Token passwordResetToken = new Token();
        passwordResetToken.setUser(user);
        passwordResetToken.setToken(token);
        passwordResetToken.setExpiryDate(Instant.ofEpochSecond(Instant.now().getEpochSecond() + 3600));
        tokenRepository.save(passwordResetToken);
    }

    public void saveNewPassword(Long id, String password) {
        User user = userRepository.findById(id).get();
        user.setPassword(passwordEncoder.encode(password));
        tokenRepository.deleteByUserId(user.getId());
        userRepository.save(user);
    }

    public void activeUser(Long id, String token) {

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
            String token = UUID.randomUUID().toString();
            createPasswordResetTokenForUser(user, token);
            String appUrl = domain + "/newPassword?id=" + user.getId() + "&token=" + token;
            LOG.info(appUrl);
            LOG.info("main thread = " + Thread.currentThread().getName());
            mailWebService.mailSend(user.getEmail(), "Restore password",
                    "reset-password-content", appUrl);
        }
    }
}
