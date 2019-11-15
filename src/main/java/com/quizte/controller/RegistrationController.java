package com.quizte.controller;

import com.quizte.dto.RegistrationUserDto;
import com.quizte.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController(value = "registration")
public class RegistrationController {

    private UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<String> registerUser(@RequestBody RegistrationUserDto registrationDto) {
        
        userService.registerUser(registrationDto);
        return ResponseEntity.ok("You've  been registered");
    }
}
