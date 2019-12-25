package com.quizter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Profile {

    @Id
    Long id;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    User user;

    String firstName;

    String lastName;

    @JsonIgnore
    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL)
    Photo photo;

    String sphere;

    String phoneNumber;
}
