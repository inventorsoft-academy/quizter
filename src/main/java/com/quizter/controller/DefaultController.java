package com.quizter.controller;

import com.quizter.entity.User;
import com.quizter.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DefaultController {

    UserService userService;

    @GetMapping("/")
    public String defaultPage() {
        log.info("defaultController");
        User user = userService.getUserPrincipal();
        if(user.getProfile() == null){
            return "redirect:/profile/edit";
        }
        switch (user.getRole()) {
            case ADMIN:
                return "redirect:/admin";
            case TEACHER:
                return "redirect:/teacher";
            case STUDENT:
                return "redirect:/desk";
            default:
                return "redirect:/login";
        }
    }
}
