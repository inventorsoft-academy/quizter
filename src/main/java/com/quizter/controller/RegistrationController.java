package com.quizter.controller;

import com.quizter.dto.MessageResponse;
import com.quizter.dto.RegistrationUserDto;
import com.quizter.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
        return "registration";
    }

    @PostMapping
    public String registration(@ModelAttribute RegistrationUserDto user) {
        System.out.println(user);
        userService.registerUser(user);
        return "redirect/";
    }
}
