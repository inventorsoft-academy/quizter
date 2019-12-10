package com.quizter.controller;

import com.quizter.dto.ProfileDto;
import com.quizter.dto.response.MessageResponse;
import com.quizter.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/profile")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileController {

    UserService userService;

    @GetMapping("/edit")
    public ModelAndView editProfilePage() {
        return new ModelAndView("profile-page");
    }

    @GetMapping
    public ModelAndView profilePage() {
        ModelAndView modelAndView = new ModelAndView("profile-page");
//        modelAndView.addObject("profileDto", profileDto);
        return modelAndView;
    }

    @PostMapping("/edit")
    public ResponseEntity<MessageResponse> editProfile(ProfileDto profileDto) {
        userService.saveProfile(profileDto);
        return ResponseEntity.ok(new MessageResponse("ok"));
    }
}
