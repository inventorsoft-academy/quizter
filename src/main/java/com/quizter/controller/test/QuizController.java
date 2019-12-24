package com.quizter.controller.test;

import com.quizter.dto.test.InviteDto;
import com.quizter.dto.test.QuizResultDto;
import com.quizter.dto.response.MessageResponse;
import com.quizter.service.test.QuizResultService;
import com.quizter.service.test.TestService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("{quizResultId}")
    public ModelAndView quizPage(@PathVariable String quizResultId) {
        ModelAndView modelAndView = new ModelAndView("quiz-page");
        modelAndView.addObject("quiz",
                testService.findTestById(quizResultService.findById(quizResultId).orElseThrow().getTest().getId()));
        modelAndView.addObject("duration", quizResultService.getDuration(quizResultId));
        return modelAndView;
    }

    @GetMapping
    public ModelAndView quizBeginPage(@RequestParam Long id) {
        ModelAndView modelAndView = new ModelAndView("quiz-begin-page");
        modelAndView.addObject("duration", testService.findTestById(id).getDuration());
        return modelAndView;
    }

    @PostMapping
    public ResponseEntity<MessageResponse> beginQuiz(@RequestParam Long id) {
        return ResponseEntity.ok(new MessageResponse(String.valueOf(quizResultService.beginQuiz(id))));
    }

    @PutMapping("{quizResultId}")
    public ResponseEntity<MessageResponse> saveChecked(@PathVariable String quizResultId,
                                                       @RequestBody List<QuizResultDto> quizResultDtos) {
        log.info("Put Request = " + quizResultDtos);
        quizResultService.updateQuiz(quizResultId, quizResultDtos);
        return ResponseEntity.ok(new MessageResponse(String.valueOf(quizResultService.getDuration(quizResultId))));
    }

    @PostMapping("{quizResultId}")
    public ResponseEntity<MessageResponse> finishQuiz(@PathVariable String quizResultId,
                                                      @RequestBody List<QuizResultDto> quizResultDtos){
        log.info("Post Request = " + quizResultDtos);
        quizResultService.finishQuiz(quizResultId, quizResultDtos);
        return ResponseEntity.ok().build();
    }
}
