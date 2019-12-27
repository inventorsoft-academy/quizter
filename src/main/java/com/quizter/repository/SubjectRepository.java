package com.quizter.repository;

import com.quizter.entity.Subject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends CrudRepository<Subject, Long> {

    Subject findSubjectByName(String name);

}
