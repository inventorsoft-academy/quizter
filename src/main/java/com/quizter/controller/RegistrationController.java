package com.quizter.controller;

import com.quizter.dictionary.Role;
import com.quizter.dto.RegistrationUserDto;
import com.quizter.dto.response.MessageResponse;
import com.quizter.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationController {

    UserService userService;

    @GetMapping(value = "/registration/{role}")
    public ModelAndView registrationForm(@PathVariable("role") Role role) {
        ModelAndView modelAndView = new ModelAndView("registration-page");
        modelAndView.addObject("role", role);
        return modelAndView;
    }

    @PostMapping(value = "/registration/{role}")
    public ResponseEntity<MessageResponse> registration(@RequestBody RegistrationUserDto user, @PathVariable("role") Role role) {
        userService.registerUser(user);
        return ResponseEntity.ok(new MessageResponse("registered"));
    }

    @GetMapping(value = "/active-account")
    public ResponseEntity<String> activateAccount(@RequestParam Long id, @RequestParam String token) {
        userService.activeUser(id, token);
        return ResponseEntity.ok("Account activated");
    }
}
