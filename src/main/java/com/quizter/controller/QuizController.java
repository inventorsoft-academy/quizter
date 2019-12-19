package com.quizter.controller;

import com.quizter.dictionary.QuestionType;
import com.quizter.dto.test.QuizResultDto;
import com.quizter.dto.response.MessageResponse;
import com.quizter.dto.test.QuestionDto;
import com.quizter.dto.test.TestDto;
import com.quizter.service.test.QuizService;
import com.quizter.service.test.TestService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/desk/quiz")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizController {

	TestService testService;
	QuizService quizService;

	@GetMapping("{id}")
	public ModelAndView quizPage(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView("quiz-page");
		TestDto test = testService.findTestById(id);
		List<QuestionDto> multivariantQuestions = test.getQuestions().stream()
				.filter(questionDto -> questionDto.getQuestionType().equals(QuestionType.MULTIVARIANT)).collect(Collectors.toList());
		List<QuestionDto> codeQuestions = test.getQuestions().stream().filter(questionDto -> questionDto.getQuestionType().equals(QuestionType.CODE))
				.collect(Collectors.toList());
		modelAndView.addObject("multivariant", multivariantQuestions);
		modelAndView.addObject("code", codeQuestions);
		modelAndView.addObject("quiz", testService.findTestById(id));
		return modelAndView;
	}

	@PostMapping("{id}")
	public ResponseEntity<MessageResponse> getRank(@PathVariable Long id, @RequestBody QuizResultDto quizResultDto) {
		log.info("Request = " + quizResultDto);
		return ResponseEntity.ok().build();//;new MessageResponse(String.valueOf(quizService.saveResult(id, quizResultDto))));
	}

	//TODO send all check to back
}
