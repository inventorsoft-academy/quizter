package com.quizter.mapper;

import com.quizter.dto.SubjectDto;
import com.quizter.entity.Subject;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubjectMapper {

    ModelMapper mapper = new ModelMapper();

    public Subject toSubject(SubjectDto subjectDto) {
        return mapper.map(subjectDto, Subject.class);
    }

    public SubjectDto toSubjectDto(Subject subject) {
        return mapper.map(subject, SubjectDto.class);
    }

    public List<SubjectDto> toSubjectListDto(List<Subject> subjects) {
        Type targetListType = new TypeToken<List<SubjectDto>>() {
        }.getType();

        return mapper.map(subjects, targetListType);
    }

}