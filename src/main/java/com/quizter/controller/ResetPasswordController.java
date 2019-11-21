package com.quizter.controller;

import com.quizter.dto.UserEmailDto;
import com.quizter.entity.User;
import com.quizter.service.MailWebService;
import com.quizter.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@AllArgsConstructor
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResetPasswordController {

    static final Logger LOG = Logger.getLogger(ResetPasswordController.class.getName());

    UserService userService;
    MailWebService mailWebService;

    @GetMapping("/resetPassword")
    public ModelAndView resetPasswordPage() {
        return new ModelAndView("reset-password-page");
    }

    @GetMapping("/resetPassword/userNotFound")
    public ModelAndView notFoundPage() {
        ModelAndView modelAndView = new ModelAndView("reset-password-page");
        modelAndView.addObject("message", "User Not Found");
        return modelAndView;
    }

    @GetMapping("/resetPassword/success")
    public ModelAndView successPage() {
        ModelAndView modelAndView = new ModelAndView("reset-password-page");
        modelAndView.addObject("checkMessage", "Check Your email");
        modelAndView.addObject("hidden", "true");
        return modelAndView;
    }

    @PostMapping(value = "/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody UserEmailDto userEmailDto) {
        LOG.info("User Email = " + userEmailDto.getUserEmail());
        String email = userEmailDto.getUserEmail();
        Optional<User> userOptional = userService.findUserByEmail(email);
        if (!userOptional.isPresent()) {
            return ResponseEntity.ok("userEmailNotFound");
        }
        User user = userOptional.get();
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);
        String appUrl = "http://localhost:8080/newPassword?id=" + user.getId() + "&token=" + token;
        mailWebService.mailSend(user.getEmail(), "Restore password", "reset-password-content", appUrl);
        return ResponseEntity.ok("success");
    }
}
