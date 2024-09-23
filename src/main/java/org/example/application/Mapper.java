package org.example.application;

import org.example.application.models.AttendanceLogModel;
import org.example.application.models.LessonModel;
import org.example.application.models.StudentModel;
import org.example.application.models.TeamModel;
import org.example.persistence.entities.AttendanceLogEntity;
import org.example.persistence.entities.LessonEntity;
import org.example.persistence.entities.StudentEntity;
import org.example.persistence.entities.TeamEntity;

import java.util.List;

public class Mapper {
    public static LessonModel toLessonModel(LessonEntity lessonEntity) {
        return new LessonModel(lessonEntity.getId(), lessonEntity.getDate());
    }

    public static StudentModel toStudentModel(StudentEntity studentEntity) {
        return new StudentModel(studentEntity.getId(), studentEntity.getName());
    }

    public static TeamModel toTeamModel(TeamEntity teamEntity, List<StudentEntity> studentEntities) {
        return new TeamModel(teamEntity.getId(), teamEntity.getName(), studentEntities.stream().map(Mapper::toStudentModel).toList());
    }

    public static AttendanceLogModel toAttendanceLogModel(AttendanceLogEntity logEntity, StudentEntity student, LessonEntity lesson) {
        return new AttendanceLogModel(logEntity.getId(), toStudentModel(student), toLessonModel(lesson), logEntity.getIsAttended());
    }
}
