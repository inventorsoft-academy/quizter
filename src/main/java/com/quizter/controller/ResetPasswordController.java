package com.quizter.controller;

import com.quizter.dto.UserEmailDto;
import com.quizter.dto.response.MessageResponse;
import com.quizter.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@AllArgsConstructor
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResetPasswordController {

    UserService userService;

    @GetMapping("/resetPassword")
    public ModelAndView resetPasswordPage() {
        return new ModelAndView("reset-password-page");
    }

    @PostMapping(value = "/resetPassword")
    public ResponseEntity<MessageResponse> resetPassword(@RequestBody UserEmailDto userEmailDto) {
        userService.resetPassword(userEmailDto);
        return ResponseEntity.ok(new MessageResponse("ok"));
    }
}
