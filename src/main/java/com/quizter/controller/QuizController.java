package com.quizter.controller;

import com.quizter.dto.test.QuizResultDto;
import com.quizter.dto.response.MessageResponse;
import com.quizter.service.test.TestService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/desk/quiz")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizController {

    TestService testService;

    @GetMapping("{id}")
    public ModelAndView quizPage(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("quiz-page");
        modelAndView.addObject("quiz", testService.findTestById(id));
        modelAndView.addObject("duration", "1");
        return modelAndView;
    }

    @PostMapping("{id}")
    public ResponseEntity<MessageResponse> getRank(@PathVariable Long id,
                                                   @RequestBody List<QuizResultDto> quizResultDtos) {
        log.info("Post Request = " + quizResultDtos);
        //TODO save results
        //TODO add time
        //TODO get rating
        //TODO fetch less data from db
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> saveChecked(@PathVariable Long id,
                                                       @RequestBody List<QuizResultDto> quizResultDtos) {
        log.info("Put Request = " + quizResultDtos);
        //TODO update results
        return ResponseEntity.ok().build();
    }
}
