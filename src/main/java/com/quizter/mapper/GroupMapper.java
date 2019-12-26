package com.quizter.mapper;

import com.quizter.dto.GroupDto;
import com.quizter.dto.StudentDto;
import com.quizter.entity.Group;
import com.quizter.entity.User;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupMapper {

    ModelMapper mapper = new ModelMapper();

    public Group toGroup(GroupDto groupDto) {
        return mapper.map(groupDto, Group.class);
    }

    public GroupDto toGroupDto(Group group) {
        return mapper.map(group, GroupDto.class);
    }

    public List<GroupDto> toGroupListDto(List<Group> groups) {
        Type targetListType = new TypeToken<List<GroupDto>>() {
        }.getType();

        return mapper.map(groups, targetListType);
    }

    public List<StudentDto> toStudentListDto(List<User> students) {
        Type targetListType = new TypeToken<List<StudentDto>>() {
        }.getType();

        return mapper.map(students, targetListType);
    }

    public List<User> toUserListDto(List<StudentDto> students) {
        Type targetListType = new TypeToken<List<User>>() {
        }.getType();

        return mapper.map(students, targetListType);
    }

    public StudentDto toStudentDto(User user) {
        return mapper.map(user, StudentDto.class);
    }

}
