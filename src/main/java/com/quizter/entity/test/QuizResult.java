package com.quizter.entity.test;

import com.quizter.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "quiz_result")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizResult {

    @Id
    String id;

    Instant start;

    Instant finished;

    Instant endOfAccessible;

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
