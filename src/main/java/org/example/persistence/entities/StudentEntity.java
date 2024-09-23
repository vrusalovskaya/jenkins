package org.example.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentEntity {
    private Integer id;
    private String name;
    private Integer teamID;
}
