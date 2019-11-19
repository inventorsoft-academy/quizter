package com.quizter.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "Users")
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "profile_id")
    Long id;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "profile")
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
