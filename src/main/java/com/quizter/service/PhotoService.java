package com.quizter.service;

import com.quizter.entity.Photo;
import com.quizter.entity.User;
import com.quizter.exception.FileUploadException;
import com.quizter.repository.PhotoRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.util.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhotoService {

    UserService userService;
    PhotoRepository photoRepository;

    public Photo savePhoto(MultipartFile file) {
        User user = userService.getUserPrincipal();
        String fileName = "user" + user.getId();
        try {
            if (fileName.contains("..")) {
                //todo https://stackoverflow.com/questions/40355743/file-upload-in-spring-boot-uploading-validation-and-exception-handling
                throw new IOException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Photo photo = user.getProfile().getPhoto();
            log.info("photo = " + photo);
            log.info("user = "+ user);
            log.info("profile = "+ user.getProfile());
            photo.setProfile(user.getProfile());
            photo.setFileName(fileName);
            photo.setFileType(file.getContentType());
            photo.setData(file.getBytes());

            return photoRepository.save(photo);
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
        return photoRepository.findByFileName("defaultPhoto").orElseThrow();
    }
}
