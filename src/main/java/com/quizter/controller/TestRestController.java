package com.quizter.controller;

import com.quizter.dto.request.TestWizardRequest;
import com.quizter.dto.response.MessageResponse;
import com.quizter.entity.test.Test;
import com.quizter.service.testservice.TestService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/testWizard")
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TestRestController {

    TestService testService;

    @GetMapping("/getAllTests")
    public ResponseEntity<List<Test>> getAllTest() {
        return ResponseEntity.ok(testService.findAllTest());
    }

    @GetMapping("/getTest/{id}")
    public ResponseEntity<Test> getTestById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(testService.findTestById(id));
    }

    @PostMapping("/createTest")
    public ResponseEntity<MessageResponse> createTest(@Valid @RequestBody TestWizardRequest testWizardRequest) {
        testService.createTest(testWizardRequest);

        return ResponseEntity.ok(new MessageResponse("Test was created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateTest(@PathVariable("id") Long id, @Valid @RequestBody TestWizardRequest testWizardRequest) {
        testService.updateTest(id, testWizardRequest);

        return ResponseEntity.ok(new MessageResponse("Test was updated successfully"));
    }

    @DeleteMapping("/deleteTest/{id}")
    public ResponseEntity deleteTest(@PathVariable("id") Long id) {
        testService.deleteTest(id);

        return ResponseEntity.noContent().build();
    }

}
