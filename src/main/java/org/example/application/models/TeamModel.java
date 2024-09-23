package org.example.application.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class TeamModel {
    private final Integer id;
    private final String name;
    private final List<StudentModel> students;
}
