package com.quizter.controller;

import com.quizter.dto.PasswordDto;
import com.quizter.dto.response.MessageResponse;
import com.quizter.service.SecurityService;
import com.quizter.service.UserService;
import com.quizter.service.ValidationService;
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
    ValidationService validation;

    @GetMapping("/newPassword")
    public ModelAndView newPasswordPage() {
        return new ModelAndView("new-password-page");
    }

    @PostMapping("/newPassword")
    public ResponseEntity<MessageResponse> saveNewPassword(@RequestParam(required = false) Long id,
                                                           @RequestParam(required = false) String token,
                                                           @RequestBody PasswordDto passwordDto) {
        if (!securityService.validateResetToken(id, token)) {
            return ResponseEntity.ok(new MessageResponse("messageWrong"));
        }
//        if (!passwordDto.getPassword().equals(passwordDto.getConfirmPassword())) {
//            return ResponseEntity.ok(new MessageResponse("passwordsMismatch"));
//        }
        validation.passwordValidation(passwordDto);
        userService.saveNewPassword(id, passwordDto.getPassword());
        return ResponseEntity.ok(new MessageResponse("newPasswordSaved"));
    }
}
