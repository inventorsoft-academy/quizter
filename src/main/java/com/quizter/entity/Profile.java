package com.quizter.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Profile {

    @Id
    Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    User user;

    @Column
    String firstName;

    @Column
    String lastName;

    @Column
    String photoUrl;

    @Column
    String sphere;
}
