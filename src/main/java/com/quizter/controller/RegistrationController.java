package com.quizter.controller;

import com.quizter.dto.MessageResponse;
import com.quizter.dto.RegistrationUserDto;
import com.quizter.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/registration")
public class RegistrationController {

    private UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<MessageResponse> registerUser(@RequestBody RegistrationUserDto registrationDto) {
        userService.registerUser(registrationDto);
        return ResponseEntity.ok(new MessageResponse("You've  been registered"));
    }
}
