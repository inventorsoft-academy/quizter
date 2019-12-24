package com.quizter.controller;

import com.quizter.dto.ProfileDto;
import com.quizter.dto.response.MessageResponse;
import com.quizter.entity.Profile;
import com.quizter.service.ProfileService;
import com.quizter.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
@RequestMapping("/profile")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileController {

    UserService userService;
    ProfileService profileService;

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

    @PutMapping("/edit")
    public ResponseEntity<MessageResponse> savePhoto(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(new MessageResponse(profileService.savePhoto(file)));
    }
}
