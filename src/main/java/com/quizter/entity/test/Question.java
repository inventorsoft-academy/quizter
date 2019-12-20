package com.quizter.entity.test;

import com.quizter.dictionary.QuestionType;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "question")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="question_type",
        discriminatorType = DiscriminatorType.STRING)
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "question_type", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    protected QuestionType questionType;

    protected String name;

    protected double price;
}
