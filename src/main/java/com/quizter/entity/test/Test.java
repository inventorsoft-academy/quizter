package com.quizter.entity.test;

import com.quizter.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    String name;

    @Column(nullable = false)
    String subject;

    @Column(nullable = false)
    String description;

    @Column(nullable = false)
    Integer duration;

    @Column(nullable = false)
    String version;

    @ManyToOne
    User author;

    @Column(nullable = false)
    @OneToMany(cascade = CascadeType.ALL)
    List<Question> questions = new ArrayList();

}