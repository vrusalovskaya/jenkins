package org.example.application.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class StudentModel {
    private final Integer id;
    private final String name;
}
