package com.quizter.entity;

import com.quizter.dictionary.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Users")
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="user_id")
    Long id;

    @Column
    String email;

    @OneToOne(cascade = CascadeType.ALL)
    Profile profile;

    @Column
    String password;

    @Column(name = "is_active")
    Boolean active;

    @Enumerated(EnumType.STRING)
    Role role;

}