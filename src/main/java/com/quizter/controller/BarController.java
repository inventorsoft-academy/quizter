package com.quizter.controller;

import com.quizter.dto.ProfileDto;
import com.quizter.service.ProfileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
public class BarController {

    ProfileService profileService;

    @GetMapping("/profile/bar")
    public ResponseEntity<ProfileDto> getData(){
        return ResponseEntity.ok(profileService.getProfileDto());
    }
}
