package org.example.application.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class AttendanceLogModel {
    private final Integer id;
    private final StudentModel student;
    private final LessonModel lesson;
    private final Boolean isAttended;
}
