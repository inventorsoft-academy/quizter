package com.quizter.controller;

import com.quizter.dto.GroupDto;
import com.quizter.dto.response.MessageResponse;
import com.quizter.service.GroupService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupController {

    GroupService groupService;

    @GetMapping("/cabinet/tests/groups")
    public ResponseEntity<List<GroupDto>> getAllGroup() {
        return ResponseEntity.ok(groupService.findAllGroup());
    }

    @PostMapping("/admin/group-create")
    public ResponseEntity<MessageResponse> createGroup(@RequestBody GroupDto groupDto) {
        groupService.createGroup(groupDto);

        return ResponseEntity.ok(new MessageResponse("Group was created successfully"));
    }

}
