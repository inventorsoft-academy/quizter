package com.quizte.controller;

import com.quizte.dto.RegistrationUserDto;
import com.quizte.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "registration")
public class RegistrationController {

    private UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody @ModelAttribute RegistrationUserDto registrationDto) {
        userService.registerUser(registrationDto);
        return ResponseEntity.ok("You've  been registered");
    }
}
