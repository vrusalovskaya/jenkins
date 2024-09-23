package org.example.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LessonEntity {
    private Integer id;
    private Long date;
}
