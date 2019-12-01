package com.quizter.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
public class ValidationException extends RuntimeException {

    Map<String, String> message;

    @Override
    public String getMessage() {
        return toString(message);
    }


    private String toString(Map<String, String> message) {
        StringBuilder mapAsString = new StringBuilder("{");
        for (String key : message.keySet()) {
            mapAsString.append("\"").append(key).append("\" : \"").append(message.get(key)).append("\", ");
        }
        mapAsString.delete(mapAsString.length() - 2, mapAsString.length()).append("}");
        return mapAsString.toString();
    }
}