package org.example.presentation.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class TeamDto {
    private Integer id;
    private String name;
    private List<StudentDto> students;
}
