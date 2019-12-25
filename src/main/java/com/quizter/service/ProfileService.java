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
import org.modelmapper.ModelMapper;
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
    PhotoService photoService;
    PhotoRepository photoRepository;

    public void saveProfile(ProfileDto profileDto) {
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
                photo = photoService.getDefaultPhoto(user);
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
}
