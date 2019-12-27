package com.quizter.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.Instant;
import java.util.Map;

//@XmlRootElement(name = "error")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class ErrorResponse {

    Instant time;
    String message;
    Map<String, String> fieldErrors;
}