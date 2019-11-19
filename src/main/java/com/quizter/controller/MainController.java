package com.quizter.controller;

import com.quizter.entity.User;
import com.quizter.service.UserService;
import com.quizter.service.mailsender.MailSender;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@AllArgsConstructor
@RestController
public class MainController {

    private UserService userService;
    private MailSender mailSender;



    @PostMapping("/resetPassword")
    public void resetPassword(@RequestBody String email){
        User user = userService.findUserByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("There is no user with email : " + email);
        }
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);
//        mailSender.sendPasswordResetToken(user);
////        return new GenericResponse(
////                messages.getMessage("message.resetPasswordEmail", null,
////                        request.getLocale()));
    }
}
