package com.quizter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
