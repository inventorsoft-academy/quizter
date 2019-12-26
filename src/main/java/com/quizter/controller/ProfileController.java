package com.quizter.controller;

import com.quizter.dto.AvatarDto;
import com.quizter.dto.ProfileDto;
import com.quizter.dto.response.MessageResponse;
import com.quizter.entity.Profile;
import com.quizter.service.ImageService;
import com.quizter.service.ProfileService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
@RequestMapping("/profile")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileController {

    ProfileService profileService;
    ImageService imageService;

    @GetMapping("/edit")
    public ModelAndView editProfilePage() {
        ModelAndView modelAndView = new ModelAndView("profile-edit-page");
        Profile profile = profileService.getCurrentUserProfile();
        modelAndView.addObject("profile", profile);
        return modelAndView;
    }

    @GetMapping
    public ModelAndView profilePage() {
        ModelAndView modelAndView = new ModelAndView("profile-page");
        Profile profile = profileService.getCurrentUserProfile();
        modelAndView.addObject("profile", profile);
        return modelAndView;
    }

    @PostMapping("/edit")
    public ResponseEntity<MessageResponse> editProfile(@RequestBody ProfileDto profileDto) {
        profileService.saveProfile(profileDto);
        return ResponseEntity.ok(new MessageResponse("ok"));
    }

    @PutMapping(value = "/edit")
    public ResponseEntity<String> savePhoto(@ModelAttribute AvatarDto file) {
        return ResponseEntity.ok(imageService.savePhoto(file));
    }
    //todo on login set photo to localStorage, on logout erase, on edit photo update
    //todo teacher review results page
}
