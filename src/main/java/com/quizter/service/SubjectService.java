package com.quizter.service;

import com.quizter.dto.SubjectDto;
import com.quizter.entity.Subject;
import com.quizter.mapper.SubjectMapper;
import com.quizter.repository.SubjectRepository;
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
public class SubjectService {

    SubjectRepository subjectRepository;

    SubjectMapper subjectMapper;

    public void createSubject(SubjectDto subjectDto) {
        Subject subject = new Subject();

        subject.setName(subjectDto.getName());

        subjectRepository.save(subject);
    }

    public SubjectDto getSubjectName(String name) {
        return null;
    }

    public List<SubjectDto> getAllSubjects() {
        return subjectMapper.toSubjectListDto(subjectRepository.findAll());
    }


}