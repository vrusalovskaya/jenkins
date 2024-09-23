package org.example.presentation.mappers;

import org.example.application.models.JournalModel;
import org.example.presentation.dtos.*;

public class Mapper {
    public static JournalDto toJournalDto(JournalModel journalModel) {
        return new JournalDto(
                journalModel.getAttendanceLogs().stream()
                        .map(al -> new AttendanceLogDto(al.getId(), al.getStudent().getId(), al.getLesson().getId(), al.getIsAttended()))
                        .toList(),
                journalModel.getLessons().stream()
                        .map(l -> new LessonDto(l.getId(), l.getDate()))
                        .toList(),
                journalModel.getTeams().stream()
                        .map(t -> new TeamDto(t.getId(), t.getName(), t.getStudents().stream().map(s -> new StudentDto(s.getId(), s.getName())).toList()))
                        .toList());
    }
}
