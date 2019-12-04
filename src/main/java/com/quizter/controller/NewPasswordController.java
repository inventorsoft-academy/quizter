package com.quizter.controller;

import com.quizter.dto.PasswordDto;
import com.quizter.dto.response.MessageResponse;
import com.quizter.service.SecurityService;
import com.quizter.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@AllArgsConstructor
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewPasswordController {

    SecurityService securityService;
    UserService userService;

    @GetMapping("/newPassword")
    public ModelAndView newPasswordPage() {
        return new ModelAndView("new-password-page");
    }

    @PostMapping("/newPassword")
    public ResponseEntity<MessageResponse> saveNewPassword(@RequestParam Long id,
                                                           @RequestParam String token,
                                                           @RequestBody PasswordDto passwordDto) {
        if (!securityService.validateResetToken(id, token)) {
            return ResponseEntity.ok(new MessageResponse("messageWrong"));
        }
        if (!passwordDto.getPassword().equals(passwordDto.getConfirmPassword())) {
            return ResponseEntity.ok(new MessageResponse("passwordsMismatch"));
        }
        userService.saveNewPassword(id, passwordDto.getPassword());
        return ResponseEntity.ok(new MessageResponse("newPasswordSaved"));
    }
}
