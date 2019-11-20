package com.quizter.controller;

import com.quizter.entity.User;
import com.quizter.service.MailWebService;
import com.quizter.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@AllArgsConstructor
@Controller
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResetPasswordController {

    static final Logger LOG = Logger.getLogger(ResetPasswordController.class.getName());

    UserService userService;
    MailWebService mailWebService;

    @GetMapping("/resetPassword")
    public String resetPasswordPage() {
        return "reset-password-page";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam String username, Model model) {
        Optional<User> userOptional = userService.findUserByEmail(username);
        if (!userOptional.isPresent()) {
            model.addAttribute("message", "User Not Found");
            return "reset-password-page";
        }
        User user = userOptional.get();
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);
        mailWebService.mailSend(user.getEmail(), "Restore password", "reset-password-content");
        model.addAttribute("checkMessage", "Check Your email");
        model.addAttribute("hidden", "true");
        return "reset-password-page";
    }
}
