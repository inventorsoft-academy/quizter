package com.quizter.controller;

import com.quizter.dto.ProfileDto;
import com.quizter.dto.response.MessageResponse;
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
        log.info("Profile controller getEdit");
        return new ModelAndView("profile-edit-page");
    }

    @GetMapping
    public ModelAndView profilePage() {
        ModelAndView modelAndView = new ModelAndView("profile-page");
//        modelAndView.addObject("profileDto", profileDto);
        //TODO render profile page
        return modelAndView;
    }

    @PostMapping("/edit")
    public ResponseEntity<MessageResponse> editProfile(@RequestBody ProfileDto profileDto) {
        log.info("Profile controller postEdit, DTO = " + profileDto.getFirstName());
        userService.saveProfile(profileDto);
        return ResponseEntity.ok(new MessageResponse("ok"));
    }
}
