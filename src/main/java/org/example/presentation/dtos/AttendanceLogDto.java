package org.example.presentation.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class AttendanceLogDto {
    private Integer id;
    private Integer studentId;
    private Integer lessonId;
    private Boolean isAttended;
}
