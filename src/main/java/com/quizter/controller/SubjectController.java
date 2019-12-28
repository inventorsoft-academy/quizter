package com.quizter.controller;

import com.quizter.dto.SubjectDto;
import com.quizter.dto.response.MessageResponse;
import com.quizter.service.SubjectService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subjects")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubjectController {

    SubjectService subjectService;

    @PostMapping("/create")
    public ResponseEntity<MessageResponse> createGroup(@RequestBody SubjectDto subjectDto) {
        subjectService.createSubject(subjectDto);

        return ResponseEntity.ok(new MessageResponse("Subject was created successfully"));
    }

    @GetMapping
    public ResponseEntity<List<SubjectDto>> getAllSubjects() {
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }
}
