package com.quizter.controller;

import com.quizter.dto.MessageResponse;
import com.quizter.entity.User;
import com.quizter.service.MailWebService;
import com.quizter.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@AllArgsConstructor
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResetPasswordRestController {

    static final Logger LOG = Logger.getLogger(ResetPasswordController.class.getName());

    UserService userService;
    MailWebService mailWebService;

    @PostMapping("/resetPassword")
    public ResponseEntity<MessageResponse> resetPassword(@RequestParam String username) {
        LOG.info("email = " + username);
        Optional<User> userOptional = userService.findUserByEmail(username);
        LOG.info("Present = " + userOptional.isPresent());
        if (!userOptional.isPresent()) {
            LOG.info("exception!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            throw new UsernameNotFoundException("There is no user with email : " + username);
        }
        User user = userOptional.get();
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);
        LOG.info("user.email = " + user.getEmail());
        mailWebService.mailSend(user.getEmail(), "Restore password", "reset-password-content");
        return ResponseEntity.ok(new MessageResponse("Check Your email"));
    }
}
