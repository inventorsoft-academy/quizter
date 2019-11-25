package com.quizter.entity;

import com.quizter.dictionary.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Users")
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "hibernate_sequence", sequenceName = "id_sequence", allocationSize = 1)
    Long id;

    @Column(unique = true)
    String email;

    @OneToOne(mappedBy = "user")
    private Profile profile;

    @Column
    String password;

    @Column(name = "is_active")
    Boolean active;

    @Enumerated(EnumType.STRING)
    Role role;

}
