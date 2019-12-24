package com.quizter.dto;

import com.quizter.entity.Subject;
import com.quizter.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupDto {

    Long id;

    @NotNull(message = "Set name of group")
    String name;

    @NotNull(message = "Select any option")
    Subject subject;

    List<User> students;

}
