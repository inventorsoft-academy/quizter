package com.quizter.service;

import com.quizter.dto.ProfileDto;
import com.quizter.entity.Profile;
import com.quizter.entity.User;
import com.quizter.repository.ProfileRepository;
import com.quizter.repository.UserRepository;
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

    UserRepository userRepository;

    ProfileRepository profileRepository;

    UserService userService;

    GroupService groupService;

    public void saveProfile(ProfileDto profileDto) {
        Profile profile = getCurrentUserProfile();
        String photoUrl = "none";
        if (profile.getPhotoUrl() != null && !"none".equals(profile.getPhotoUrl())) {
            photoUrl = "/images/user" + profile.getUser().getId() + ".jpg";
        }
        profile.setFirstName(profileDto.getFirstName());
        profile.setLastName(profileDto.getLastName());
        profile.setSphere(profileDto.getSphere());
        profile.setPhoneNumber(profileDto.getPhoneNumber());
        profile.setPhotoUrl(photoUrl);
        profile.setId(profile.getUser().getId());
        profileRepository.save(profile);

    }

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


    public Profile getCurrentUserProfile() {
        User user = userService.getUserPrincipal();
        if (user.getProfile() == null) {
            Profile profile = new Profile();
            user.setProfile(profile);
            profile.setUser(user);
            profile.setId(user.getId());
            profileRepository.save(profile);
            userRepository.save(user);
            return profile;
        }
        return user.getProfile();
    }
}
