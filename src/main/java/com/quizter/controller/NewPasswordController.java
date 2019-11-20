package com.quizter.controller;

import com.quizter.entity.User;
import com.quizter.service.SecurityService;
import com.quizter.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.logging.Logger;

@AllArgsConstructor
@Controller
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewPasswordController {

    SecurityService securityService;
    UserService userService;

    static final Logger LOG = Logger.getLogger(NewPasswordController.class.getName());

    @GetMapping("/newPassword")
    public String newPasswordPage(@RequestParam Long id, @RequestParam String token) {
        String result = securityService.validateResetToken(id, token);
        return "new-password-page";
    }

    @PostMapping("/newPassword")
    public String saveNewPassword(@RequestParam String password) {
        LOG.info("Principal = " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.saveNewPassword(user, password);
        return "redirect:/login";
    }
}
