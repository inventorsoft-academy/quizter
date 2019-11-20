package com.quizter.service;

import com.quizter.entity.PasswordResetToken;
import com.quizter.entity.User;
import com.quizter.repository.PasswordRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.logging.Logger;

@AllArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SecurityService {

    static final Logger LOG = Logger.getLogger(SecurityService.class.getName());

    PasswordRepository passwordRepository;

    public String validateResetToken(Long id, String token) {
        PasswordResetToken passwordResetToken = passwordRepository.findByToken(token);
        if (passwordResetToken == null || passwordResetToken.getUser().getId() != id) {
            return "invalidToken";
        }

        Calendar calendar = Calendar.getInstance();
        if ((passwordResetToken.getExpiryDate().getTime() - calendar.getTime().getTime() < 0)) {
            return "expired";
        }

        User user = passwordResetToken.getUser();
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user, null, Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return null;
    }
}
