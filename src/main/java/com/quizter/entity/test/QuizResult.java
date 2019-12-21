package com.quizter.entity.test;

import com.quizter.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "quiz_result")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizResult {
    //todo show available and passed quizzes for Student
    //TODO update quiz page + relogin
    //TODO bind student with his open test + add students
    //using quizResult
    // add testCompleted
//TODO save results
//TODO add time synchro
//TODO get rating
//TODO fetch less data from db


    @Id
    String id;

    Instant start;

    Instant finished;

    Boolean isCompleted;

    @ManyToOne(cascade = CascadeType.ALL)
    User applicant;

    @ManyToOne
    Test test;

    @Column(nullable = false)
    double totalRating;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quizResult")
    List<ResultAnswer> resultAnswers;
}
