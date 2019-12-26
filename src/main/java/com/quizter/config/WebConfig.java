package com.quizter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login-page");
        registry.addViewController("/cabinet/test-create").setViewName("test-creation-page");
        registry.addViewController("/cabinet/tests").setViewName("tests-page");
        registry.addViewController("/active-account-page").setViewName("active-account-page");
        registry.addViewController("/admin").setViewName("admin-page");
        registry.addViewController("/cabinet").setViewName("cabinet-page");
        registry.addViewController("/cabinet/students").setViewName("students-page");
        registry.addViewController("/cabinet/student-groups").setViewName("student-groups-page");
    }
}
