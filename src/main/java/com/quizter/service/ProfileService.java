package com.quizter.service;

import com.quizter.dto.ProfileDto;
import com.quizter.entity.Photo;
import com.quizter.entity.Profile;
import com.quizter.entity.User;
import com.quizter.repository.PhotoRepository;
import com.quizter.repository.ProfileRepository;
import com.quizter.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Base64;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileService {

    // todo save photo to DB byte64
    // todo show available and passed quizzes for Student
    // TODO bind student with his open test + add students
    // todo filter available tests by quizResult isCompleted
    // todo for student show only is correct
    // todo update quiz page with checked
    // todo finish quiz alert without rating
    // todo Async evaluation

    UserService userService;
    UserRepository userRepository;
    ProfileRepository profileRepository;
    ImageService imageService;
    PhotoRepository photoRepository;
    ValidationService validationService;


    public void saveProfile(ProfileDto profileDto) {
        log.info("profileDto = " + profileDto);
        validationService.validateProfile(profileDto);
        Profile profile = getCurrentUserProfile();
        Photo photo = profile.getPhoto();
        profile.setFirstName(profileDto.getFirstName());
        profile.setLastName(profileDto.getLastName());
        profile.setSphere(profileDto.getSphere());
        profile.setPhoneNumber(profileDto.getPhoneNumber());
        profile.setPhoto(photo);
        profile.setId(profile.getUser().getId());
        profileRepository.save(profile);
    }

    public Profile getCurrentUserProfile() {
        User user = userService.getUserPrincipal();
        if (user.getProfile() == null) {
            Profile profile = new Profile();
            Photo photo = null;
            try {
                photo = imageService.getDefaultPhoto(user);
            } catch (IOException e) {
                e.printStackTrace();
            }
            profile.setPhoto(photo);
            photo.setProfile(profile);
            user.setProfile(profile);
            profile.setUser(user);
            profile.setId(user.getId());
            profileRepository.save(profile);
            photoRepository.save(photo);
            userRepository.save(user);
            return profile;
        }
        return user.getProfile();
    }

    public ProfileDto getProfileDto() {
        ProfileDto profileDto = new ProfileDto();
        User user = userService.getUserPrincipal();
        profileDto.setFirstName(user.getProfile().getFirstName());
        profileDto.setLastName(user.getProfile().getLastName());
        profileDto.setPhoto(Base64.getEncoder().encodeToString(user.getProfile().getPhoto().getData()));
        return profileDto;
    }
}
