package com.quizter.controller;

import com.quizter.dto.test.QuizResultDto;
import com.quizter.dto.response.MessageResponse;
import com.quizter.service.test.QuizResultService;
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
    QuizResultService quizResultService;

    @GetMapping("{id}")
    public ModelAndView quizPage(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("quiz-page");
        modelAndView.addObject("quiz",
                testService.findTestById(quizResultService.findById(id).orElseThrow().getTest().getId()));
        modelAndView.addObject("duration", "1");
        return modelAndView;
    }

    @GetMapping
    public ModelAndView quizBeginPage(@RequestParam Long id) {
        ModelAndView modelAndView = new ModelAndView("quiz-begin-page");
//        modelAndView.addObject("duration", testService.findTestById(id).getDuration());
        modelAndView.addObject("duration", "1");
        return modelAndView;
    }

    @PostMapping
    public ResponseEntity<MessageResponse> beginQuiz(@RequestParam Long id) {
        return ResponseEntity.ok(new MessageResponse(String.valueOf(quizResultService.beginQuiz(id))));
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> saveChecked(@PathVariable Long id,
                                                       @RequestBody List<QuizResultDto> quizResultDtos) {
        log.info("Put Request = " + quizResultDtos);
        //TODO update results
        quizResultService.updateQuiz(id, quizResultDtos);
        return ResponseEntity.ok().build();
    }

    @PostMapping("{id}")
    public ResponseEntity<MessageResponse> finishQuiz(@PathVariable Long id,
                                                      @RequestBody List<QuizResultDto> quizResultDtos){
        log.info("Post Request = " + quizResultDtos);
        quizResultService.updateQuiz(id, quizResultDtos);
        return ResponseEntity.ok().build();
    }
}
