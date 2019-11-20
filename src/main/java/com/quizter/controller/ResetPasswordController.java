package com.quizter.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Controller
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResetPasswordController {

    @GetMapping("/resetPassword")
    public String resetPasswordPage() {
        return "reset-password-page";
    }

}
