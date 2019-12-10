package com.quizter.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeskController {

    @GetMapping("/desk")
    public ModelAndView deskPage() {
        return new ModelAndView("default-page");
    }
}
