package com.quizter.controller;

import com.quizter.dto.test.TestDto;
import com.quizter.entity.test.QuizResult;
import com.quizter.mapper.test.TestMapper;
import com.quizter.service.test.QuizResultService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeskController {

    TestMapper testMapper;

    QuizResultService quizResultService;

    @GetMapping("/desk")
    public ModelAndView deskPage() {
        ModelAndView modelAndView = new ModelAndView("desk-page");

        List<TestDto> quizzes = testMapper.toTestListDto(quizResultService.getAccessibleTestsForStudent());
        List<QuizResult> passedQuizzes = quizResultService.findByApplicant();

        modelAndView.addObject("quizzes", quizzes);
        modelAndView.addObject("passedQuizzes", passedQuizzes);

        return modelAndView;
    }
}