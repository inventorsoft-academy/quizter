package com.quizter.controller;

import com.quizter.dto.ProfileDto;
import com.quizter.dto.response.MessageResponse;
import com.quizter.entity.Photo;
import com.quizter.entity.Profile;
import com.quizter.service.PhotoService;
import com.quizter.service.ProfileService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;

@Slf4j
@RestController
@RequestMapping("/profile")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileController {

    ProfileService profileService;
    PhotoService photoService;

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
    public ResponseEntity<InputStreamResource> savePhoto(@RequestParam("file") MultipartFile file) {
//        Photo photo = photoService.savePhoto(file);
//        byte[] data = photo.getData();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
//        return new ResponseEntity<>(data, headers, HttpStatus.OK);


        Photo photo = photoService.savePhoto(file);

        byte[] data = photo.getData();
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        //TODO render saved photo
        log.info("file = " + file);

        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(photo.getData()));
        log.info("resource = " + resource);
        return ResponseEntity.ok()
                .header(String.valueOf(headers))
                .contentLength(photo.getData().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
}
