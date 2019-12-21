package com.quizter.entity.test;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.List;

@Entity
public class MultiAnswer extends Answer {

    @ElementCollection
    List<String> answerList;
}
