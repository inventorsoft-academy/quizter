package com.quizter.entity.test;

import com.quizter.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
