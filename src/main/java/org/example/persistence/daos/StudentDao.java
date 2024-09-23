package org.example.persistence.daos;

import org.example.persistence.entities.StudentEntity;

import java.sql.SQLException;
import java.util.List;

public class StudentDao extends DaoBase {

    private static final String STUDENT_ID_COLUMN_NAME = "id";
    private static final String NAME_COLUMN_NAME = "name";
    private static final String TEAM_ID_COLUMN_NAME = "team_id";
    private static final String TABLE_NAME = "Student";

    private static final String SELECT_FROM_STUDENT_SCRIPT = "SELECT * FROM " + TABLE_NAME;

    public StudentDao(String url, String userName, String password) {
        super(url, userName, password);
    }

    public List<StudentEntity> getStudents() throws SQLException {
        return getList(SELECT_FROM_STUDENT_SCRIPT, result -> {
            int studentId = result.getInt(STUDENT_ID_COLUMN_NAME);
            String studentName = result.getString(NAME_COLUMN_NAME);
            int teamId = result.getInt(TEAM_ID_COLUMN_NAME);
            return new StudentEntity(studentId, studentName, teamId);
        });
    }

}
