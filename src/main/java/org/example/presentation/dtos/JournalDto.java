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
public class JournalDto {
    private List<AttendanceLogDto> attendanceLogs;
    private List<LessonDto> lessons;
    private List<TeamDto> teams;
}
