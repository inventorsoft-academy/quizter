package com.quizter.service;

import com.quizter.dto.GroupDto;
import com.quizter.dto.StudentDto;
import com.quizter.entity.Group;
import com.quizter.entity.User;
import com.quizter.exception.ResourceNotFoundException;
import com.quizter.mapper.GroupMapper;
import com.quizter.repository.GroupRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupService {

    GroupRepository groupRepository;

    GroupMapper groupMapper;

    public List<GroupDto> findAllGroup() {
        return groupMapper.toGroupListDto(groupRepository.findAll());
    }

    public List<StudentDto> findAllStudentsFromGroupByGroupId(Long id) {
        Group group = groupRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Group", "id", id));

        return groupMapper.toStudentListDto(group.getStudents());
    }

    public void createGroup(GroupDto groupDto) {
        Group group = new Group();
        group.setName(groupDto.getName());

        groupRepository.save(group);
    }

    public void setUserToGroup(User user) {
        Group group = groupRepository.findGroupByName(user.getProfile().getSphere())
                .orElseThrow(() -> new ResourceNotFoundException("Group", "Sphere", user.getProfile().getSphere()));

        List<User> studentsFromDB = group.getStudents();
        studentsFromDB.add(user);

        group.setStudents(studentsFromDB);

        groupRepository.save(group);
    }


}