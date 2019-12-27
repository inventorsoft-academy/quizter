package com.quizter.service;

import com.quizter.dto.AvatarDto;
import com.quizter.entity.Photo;
import com.quizter.entity.User;
import com.quizter.exception.FileUploadException;
import com.quizter.exception.ResourceNotFoundException;
import com.quizter.exception.UserIsNotAuthorizedException;
import com.quizter.repository.PhotoRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageService {

    UserService userService;
    PhotoRepository photoRepository;
    ValidationService validationService;

    public String savePhoto(AvatarDto avatarDto) {
        validationService.validateImage(avatarDto);
        MultipartFile file = avatarDto.getFile();
        User user = userService.getUserPrincipal().orElseThrow(UserIsNotAuthorizedException::new);
        String fileName = "user" + user.getId();
        try {
            if (fileName.contains("..")) {
                throw new IOException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Photo photo = user.getProfile().getPhoto();
            log.info("photo = " + photo);
            log.info("user = " + user);
            log.info("profile = " + user.getProfile());
            photo.setProfile(user.getProfile());
            photo.setFileName(fileName);
            photo.setFileType(file.getContentType());
            photo.setData(file.getBytes());
            byte[] data = photoRepository.save(photo).getData();
            return Base64.getEncoder().encodeToString(data);
        } catch (IOException ex) {
            throw new FileUploadException("Could not store file " + fileName + ". Please try again!");
        }
    }

    public Photo findById(Long id) {
        return photoRepository.findById(id)
                .orElseThrow(() -> new FileUploadException("File not found with id " + id));
    }

    public Photo getDefaultPhoto(User user) throws IOException {
        if (photoRepository.findByFileName("defaultPhoto").isEmpty()) {
            Photo photo = new Photo();
            photo.setFileName("defaultPhoto");
            File file = new File("src/main/resources/static/images/img.jpg");
            photo.setFileType(".jpg");
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(file);
                photo.setData(fileInputStream.readAllBytes());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                fileInputStream.close();
            }
            photoRepository.save(photo);
        }
        return photoRepository.findByFileName("defaultPhoto")
                .orElseThrow(() -> new ResourceNotFoundException("phot", "defaultPhoto", "defaultPhoto"));
    }
}
