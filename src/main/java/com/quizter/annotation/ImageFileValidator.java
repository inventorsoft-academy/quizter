package com.quizter.annotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

@Slf4j
public class ImageFileValidator implements ConstraintValidator<ValidImage, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {

        if (!isSupportedFileType(Objects.requireNonNull(file.getContentType()))) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Only JPEG images allowed")
                    .addPropertyNode("upload").addConstraintViolation();
            return false;
        }

        if (file.getSize() > 50000) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("The file size is too large")
                    .addPropertyNode("upload").addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean isSupportedFileType(String contentType) {
        return contentType.equals("image/jpg")
                || contentType.equals("image/jpeg");
    }
}
