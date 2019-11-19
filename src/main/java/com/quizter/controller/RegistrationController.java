package com.quizter.controller;

import com.quizter.dictionary.Role;
import com.quizter.dto.RegistrationUserDto;
import com.quizter.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/registration")
public class RegistrationController {

    private UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String registrationForm(Model model) {
        model.addAttribute("user", new RegistrationUserDto());
        return "registration-page";
    }

    @PostMapping(value = "/{role}")
    public String registration(@ModelAttribute RegistrationUserDto user, @PathVariable(name = "role") Role role) {
        user.setRole(role);
        userService.registerUser(user);
        return "redirect/login";
    }
}
