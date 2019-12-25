package com.quizter.controller.teacher;

import com.quizter.dto.response.MessageResponse;
import com.quizter.dto.test.TestDto;
import com.quizter.service.test.TestService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cabinet/tests/")
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TeacherRestController {

	TestService testService;

	@GetMapping
	public ResponseEntity<List<TestDto>> getAllTest() {
		return ResponseEntity.ok(testService.findAllTest());
	}

	@GetMapping("/{id}")
	public ResponseEntity<TestDto> getTestById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(testService.findTestById(id));
	}

	@PostMapping
	public ResponseEntity<MessageResponse> createTest(@RequestBody TestDto testDto) {
		testService.createTest(testDto);

		return ResponseEntity.ok(new MessageResponse("Test was created successfully"));
	}

	@PutMapping("/{id}")
	public ResponseEntity<MessageResponse> updateTest(@PathVariable("id") Long id, @RequestBody TestDto testDto) {
		testService.updateTest(id, testDto);

		return ResponseEntity.ok(new MessageResponse("Test was updated successfully"));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deleteTest(@PathVariable("id") Long id) {
		testService.deleteTest(id);

		return ResponseEntity.noContent().build();
	}

}
