package com.quizter.service;

import com.quizter.entity.Profile;
import com.quizter.entity.User;
import com.quizter.repository.ProfileRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileService {

    UserService userService;
    ProfileRepository profileRepository;

    public String savePhoto(MultipartFile file) {
        User user = userService.getUserPrincipal();
        byte[] bytes;
        try {
            bytes = file.getBytes();
            Path path = Paths.get("src/main/resources/static/images/user" + user.getId() + ".jpg");
            Files.write(path, bytes);
            path = Paths.get("target/classes/static/images/user" + user.getId() + ".jpg");
            Files.write(path, bytes);
        } catch (IOException e) {
            log.info("Image not saved, " + e);
        }
        Profile profile = user.getProfile();
        profile.setPhotoUrl("/images/user" + user.getId() + ".jpg");
        profileRepository.save(profile);
        return profileRepository.findById(profile.getId()).orElseThrow().getPhotoUrl();
    }

}
