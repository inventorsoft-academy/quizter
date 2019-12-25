package com.quizter.controller;

import com.quizter.annotation.ValidImage;
import com.quizter.dto.ProfileDto;
import com.quizter.dto.response.MessageResponse;
import com.quizter.entity.Photo;
import com.quizter.entity.Profile;
import com.quizter.service.ImageService;
import com.quizter.service.ProfileService;
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

import java.util.Base64;

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
    public ResponseEntity<String> savePhoto(@RequestParam("file") MultipartFile file) {
        Photo photo = imageService.savePhoto(file);
        byte[] data = photo.getData();
        String base64data = Base64.getEncoder().encodeToString(data);
        return ResponseEntity.ok(base64data);
    }

}
