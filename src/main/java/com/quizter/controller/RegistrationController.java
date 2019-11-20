package com.quizter.controller;

import com.quizter.dictionary.Role;
import com.quizter.dto.RegistrationUserDto;
import com.quizter.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/registration/{role}")
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

    @PostMapping
    public String registration(@ModelAttribute RegistrationUserDto user, @PathVariable("role") Role role) {
        userService.registerUser(user);
        return "redirect:/login";
    }
}
