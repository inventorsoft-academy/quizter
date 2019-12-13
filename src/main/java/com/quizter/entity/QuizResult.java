package com.quizter.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "quiz_result")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizResult {

    @Id
    @GeneratedValue
    long id;

    String testName;

    @ManyToOne
    User user;

    @ElementCollection
    @CollectionTable(name = "quiz_result_results",
            joinColumns = {@JoinColumn(name = "quiz_result_id", referencedColumnName = "id")})
    Map<String, String> results;

    @ElementCollection
    @CollectionTable(name = "quiz_result_right_results",
            joinColumns = {@JoinColumn(name = "quiz_result_id", referencedColumnName = "id")})
    Map<String, String> rightResults;

}
