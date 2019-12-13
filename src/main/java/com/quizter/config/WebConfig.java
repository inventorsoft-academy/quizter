package com.quizter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login-page");
        registry.addViewController("/test-create").setViewName("test-creation-page");
        registry.addViewController("/tests").setViewName("tests-page");
        registry.addViewController("/active-account-page").setViewName("active-account-page");
        registry.addViewController("/admin").setViewName("default-page");
        registry.addViewController("/teacher").setViewName("default-page");
    }
}
