package com.quizter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Photo {

    @Id
    @GeneratedValue
    Long id;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "profile_id")
    Profile profile;

    String fileName;

    String fileType;

    @Lob
    byte[] data;

}
