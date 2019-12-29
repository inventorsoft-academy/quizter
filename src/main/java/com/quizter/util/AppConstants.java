package com.quizter.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppConstants {

    String host;

}
