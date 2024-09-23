package org.example.application.service;

import lombok.AllArgsConstructor;
import org.example.application.Mapper;
import org.example.application.models.AttendanceLogModel;
import org.example.application.models.JournalModel;
import org.example.application.models.TeamModel;
import org.example.persistence.daos.AttendanceLogDao;
import org.example.persistence.daos.LessonDao;
import org.example.persistence.daos.StudentDao;
import org.example.persistence.daos.TeamDao;
import org.example.persistence.entities.LessonEntity;
import org.example.persistence.entities.StudentEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class JournalService {
    private final AttendanceLogDao attendanceLogDao;
    private final LessonDao lessonDao;
    private final StudentDao studentDao;
    private final TeamDao teamDao;

    public JournalModel getJournal() throws SQLException {
        List<LessonEntity> lessonEntities = lessonDao.getLessons();
        List<StudentEntity> studentEntities = studentDao.getStudents();

        List<TeamModel> teams = teamDao.getTeams().stream()
                .map(teamEntity -> {
                    List<StudentEntity> studentsPerTeam = studentEntities.stream()
                            .filter(studentEntity -> Objects.equals(studentEntity.getTeamID(), teamEntity.getId()))
                            .toList();
                    return Mapper.toTeamModel(teamEntity, studentsPerTeam);
                })
                .toList();

        List<AttendanceLogModel> attendanceLogs = attendanceLogDao.getAttendanceLogs().stream()
                .map(logEntity -> {
                    StudentEntity studentEntity = studentEntities.stream()
                            .filter(se -> Objects.equals(se.getId(), logEntity.getStudentId()))
                            .findFirst()
                            .get();
                    LessonEntity lessonEntity = lessonEntities.stream()
                            .filter(le -> Objects.equals(le.getId(), logEntity.getLessonId()))
                            .findFirst()
                            .get();
                    return Mapper.toAttendanceLogModel(logEntity, studentEntity, lessonEntity);
                })
                .toList();

        return new JournalModel(
                attendanceLogs,
                lessonEntities.stream().map(Mapper::toLessonModel).toList(),
                teams);
    }

    public Integer createLesson(Long date) throws SQLException {
        // TODO: add validation for dates in past?
        return lessonDao.createLesson(date);
    }

    public Integer createAttendanceLog(Integer studentId, Integer lessonId, Boolean isAttended) throws SQLException {
        return attendanceLogDao.createAttendanceLog(studentId, lessonId, isAttended);
    }
}
