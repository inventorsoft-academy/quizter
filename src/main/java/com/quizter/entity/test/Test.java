package com.quizter.entity.test;

import com.quizter.entity.Subject;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.Instant;
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

    @Column(nullable = false)
    String name;

    @OneToOne
    Subject subject;

    @Column(nullable = false)
    String description;

    @Column(nullable = false)
    Integer duration;

    @Column(unique = true, nullable = false)
    Instant version;

    Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    User author;

    @Column(nullable = false)
    @OneToMany(cascade = CascadeType.ALL)
    List<Question> questions = new ArrayList();

}