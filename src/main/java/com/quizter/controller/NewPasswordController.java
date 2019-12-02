package com.quizter.controller;

import com.quizter.dto.PasswordDto;
import com.quizter.entity.User;
import com.quizter.service.SecurityService;
import com.quizter.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.logging.Logger;

@AllArgsConstructor
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewPasswordController {

    SecurityService securityService;
    UserService userService;

    static final Logger LOG = Logger.getLogger(NewPasswordController.class.getName());

    @GetMapping("/newPassword")
    public ModelAndView newPasswordPage(@RequestParam Long id, @RequestParam String token) {
        ModelAndView modelAndView = new ModelAndView("new-password-page");
        String result = securityService.validateResetToken(id, token);
        if (result != "success") {
            modelAndView.addObject("messageWrong", "something wrong, try again");
            modelAndView.addObject("hidden", "true");
        }
        return modelAndView;
    }

    @GetMapping("/newPassword/passwordsMismatch")
    public ModelAndView newPasswordMismatchPage() {
        ModelAndView modelAndView = new ModelAndView("new-password-page");
        modelAndView.addObject("message", "Passwords mismatch");
        return modelAndView;
    }

    @PostMapping("/newPassword")
    public ResponseEntity<String> saveNewPassword(@RequestBody PasswordDto newPasswordDto) {
        if (!newPasswordDto.getPassword().equals(newPasswordDto.getConfirmPassword())) {
            return ResponseEntity.ok("passwordsMismatch");
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.saveNewPassword(user, newPasswordDto.getPassword());
        return ResponseEntity.ok("newPasswordSaved");
    }
}
