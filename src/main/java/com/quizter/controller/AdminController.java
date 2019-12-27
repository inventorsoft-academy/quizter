package com.quizter.controller;

import com.quizter.dto.GroupDto;
import com.quizter.dto.InviteDto;
import com.quizter.dto.StudentDto;
import com.quizter.dto.SubjectDto;
import com.quizter.dto.response.MessageResponse;
import com.quizter.service.GroupService;
import com.quizter.service.SubjectService;
import com.quizter.service.UserService;
import com.quizter.service.test.QuizResultService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminController {

    SubjectService subjectService;

    GroupService groupService;

    UserService userService;

    QuizResultService quizResultService;

    @PostMapping("/subject/create")
    public ResponseEntity<MessageResponse> createGroup(@RequestBody SubjectDto subjectDto) {
        subjectService.createSubject(subjectDto);

        return ResponseEntity.ok(new MessageResponse("Subject was created successfully"));
    }

    @GetMapping("/subjects")
    public ResponseEntity<List<SubjectDto>> getAllSubjects() {
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }

    @GetMapping("/tests/groups")
    public ResponseEntity<List<GroupDto>> getAllGroup() {
        return ResponseEntity.ok(groupService.findAllGroup());
    }

    @PostMapping("/group-create")
    public ResponseEntity<MessageResponse> createGroup(@RequestBody GroupDto groupDto) {
        groupService.createGroup(groupDto);

        return ResponseEntity.ok(new MessageResponse("Group was created successfully"));
    }

    @GetMapping("/students/subject/{subjectName}")
    public ResponseEntity<List<StudentDto>> getStudentsBySubject(@PathVariable("subjectName") String subjectName) {
        return ResponseEntity.ok(userService.findStudentsBySubjectName(subjectName));
    }

    @GetMapping("/students/{email}")
    public ResponseEntity<StudentDto> getStudentsByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(userService.findStudentByEmail(email));
    }

    @GetMapping("/students")
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        return ResponseEntity.ok(userService.findAllStudents());
    }

    @GetMapping("/tests/groups/{id}/students-from-group")
    public ResponseEntity<List<StudentDto>> getAllStudentsFromGroupByGroupId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(groupService.findAllStudentsFromGroupByGroupId(id));
    }

    @GetMapping("/tests/student-groups/students")
    public ModelAndView getStudentPageFromGroupPage() {
        return new ModelAndView("students-page");
    }

    @GetMapping("/tests/student-groups/{id}/students")
    public ModelAndView getStudentPageFromGroupPage(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("students-page");
        modelAndView.addObject("groupId", id);
        return modelAndView;
    }

    @GetMapping("/tests/student-groups")
    public ModelAndView getGroupPage() {
        return new ModelAndView("student-groups-page");
    }

    @GetMapping("/tests/student-groups/{id}")
    public ModelAndView getGroupPageByTestId(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("student-groups-page");
        modelAndView.addObject("testIdForGroup", id);
        return modelAndView;
    }

    @PostMapping("/tests/invite-group")
    public ResponseEntity<MessageResponse> inviteStudentsToTest(@RequestBody InviteDto inviteDto) {
        inviteDto.getStudents().forEach(e ->
                quizResultService.addAccessToTest(e, inviteDto.getTestId()));

        return ResponseEntity.ok(new MessageResponse("Group was invited successfully"));
    }

}

