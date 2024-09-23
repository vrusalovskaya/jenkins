package org.example.presentation.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AttendanceLogRequest {
    private Integer studentId;
    private Integer lessonId;
    private Boolean attended;
}
