package com.quizter.controller;

import com.quizter.service.test.QuizResultService;
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

    QuizResultService quizResultService;

    @GetMapping("/desk")
    public ModelAndView deskPage() {
        ModelAndView modelAndView = new ModelAndView("desk-page");
        modelAndView.addObject("quizzes", quizResultService.getAvailableQuizzes());
        modelAndView.addObject("passedQuizzes", quizResultService.getPassedQuizzes());
        return modelAndView;
    }
}
