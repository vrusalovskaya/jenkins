package org.example.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttendanceLogEntity {
    private Integer id;
    private Integer studentId;
    private Integer lessonId;
    private Boolean isAttended;
}
