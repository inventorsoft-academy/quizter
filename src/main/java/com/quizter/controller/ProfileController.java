package com.quizter.controller;

import com.quizter.dto.ProfileDto;
import com.quizter.dto.response.MessageResponse;
import com.quizter.entity.Profile;
import com.quizter.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
@RequestMapping("/profile")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileController {

    UserService userService;

    @GetMapping("/edit")
    public ModelAndView editProfilePage() {
        ModelAndView modelAndView = new ModelAndView("profile-edit-page");
        Profile profile = userService.getUserPrincipal().getProfile();
        modelAndView.addObject("profile", profile);
        return modelAndView;
    }

    @GetMapping
    public ModelAndView profilePage() {
        ModelAndView modelAndView = new ModelAndView("profile-page");
        Profile profile = userService.getUserPrincipal().getProfile();
        modelAndView.addObject("profile", profile);
        return modelAndView;
    }

    @PostMapping("/edit")
    public ResponseEntity<MessageResponse> editProfile(@RequestBody ProfileDto profileDto) {
        userService.saveProfile(profileDto);
        return ResponseEntity.ok(new MessageResponse("ok"));
    }
}
