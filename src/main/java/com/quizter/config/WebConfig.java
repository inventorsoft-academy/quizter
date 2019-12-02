package com.quizter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author anatolii vakaliuk
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home-page");
        registry.addViewController("/login").setViewName("login-page");
        registry.addViewController("/createTest").setViewName("testcreation-page");
        registry.addViewController("/tests").setViewName("tests-page");
    }
}
