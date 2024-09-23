package org.example.application.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
public class JournalModel {
    private final List<AttendanceLogModel> attendanceLogs;
    private final List<LessonModel> lessons;
    private final List<TeamModel> teams;
}
