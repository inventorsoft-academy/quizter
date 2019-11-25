package com.quizter.controller;

import com.quizter.dto.MessageResponse;
import com.quizter.dto.RegistrationUserDto;
import com.quizter.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/registration")
@AllArgsConstructor
public class RegistrationController {

    private UserService userService;

    @PostMapping
    public ResponseEntity<MessageResponse> registerUser(@RequestBody RegistrationUserDto registrationDto) {
        userService.registerUser(registrationDto);
        return ResponseEntity.ok(new MessageResponse("You've  been registered"));
    }
}
