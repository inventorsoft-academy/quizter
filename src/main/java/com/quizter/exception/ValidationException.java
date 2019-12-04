package com.quizter.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class ValidationException extends RuntimeException {

    Map<String, String> validationErrors;

    public ValidationException(Map<String, String> message) {
        super("Validation error");
        this.validationErrors = message;
    }


    //
//    private String toString(Map<String, String> message) {
//        StringBuilder mapAsString = new StringBuilder("{");
//        for (String key : message.keySet()) {
//            mapAsString.append("\"").append(key).append("\" : \"").append(message.get(key)).append("\", ");
//        }
//        mapAsString.delete(mapAsString.length() - 2, mapAsString.length()).append("}");
//        return mapAsString.toString();
//    }
}