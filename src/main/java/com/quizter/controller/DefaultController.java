package com.quizter.controller;

import com.quizter.entity.Credentials;
import com.quizter.entity.User;
import com.quizter.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DefaultController {

    UserService userService;

    @GetMapping("/")
    public String defaultPage() {
        User user = userService.getUserPrincipal();
        if(user.getProfile() == null){
            return "redirect:/profile/edit";
        }
        switch (user.getRole()) {
            case ADMIN:
                return "redirect:/admin";
            case TEACHER:
                return "redirect:/teacher";
            default:
                return "redirect:/desk";
        }
    }
}
