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

    @Id
    @GeneratedValue
    long id;

    Instant start;

    Instant finished;

    @ManyToOne(cascade = CascadeType.ALL)
    User applicant;

    @ManyToOne
    Test test;

    @Column(nullable = false)
    double totalRating;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quizResult")
    List<ResultAnswer> resultAnswers;
}
