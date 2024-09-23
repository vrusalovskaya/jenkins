package org.example.persistence.daos;

import org.example.persistence.entities.AttendanceLogEntity;

import java.sql.*;
import java.util.List;

public class AttendanceLogDao extends DaoBase {
    private static final String ID_COLUMN_NAME = "id";
    private static final String STUDENT_ID_COLUMN_NAME = "student_id";
    private static final String LESSON_ID_COLUMN_NAME = "lesson_id";
    private static final String ATTENDED_COLUMN_NAME = "attended";
    private static final String TABLE_NAME = "AttendanceLog";

    private static final String SELECT_FROM_ATTENDANCE_LOG_SCRIPT = "SELECT * FROM " + TABLE_NAME;
    private static final String INSERT_INTO_ATTENDANCE_LOG_SCRIPT =  "INSERT INTO " + TABLE_NAME + " ("
            + STUDENT_ID_COLUMN_NAME + ", "
            + LESSON_ID_COLUMN_NAME + ", "
            + ATTENDED_COLUMN_NAME + ") VALUES (?, ?, ?)";

    public AttendanceLogDao(String url, String userName, String password) {
        super(url, userName, password);
    }


    public List<AttendanceLogEntity> getAttendanceLogs() throws SQLException {

        return getList(SELECT_FROM_ATTENDANCE_LOG_SCRIPT, result -> {
            int id = result.getInt(ID_COLUMN_NAME);
            int studentId = result.getInt(STUDENT_ID_COLUMN_NAME);
            int lessonId = result.getInt(LESSON_ID_COLUMN_NAME);
            Boolean isAttended = result.getBoolean(ATTENDED_COLUMN_NAME);
            return new AttendanceLogEntity(id, studentId, lessonId, isAttended);
        });
    }

   public Integer createAttendanceLog(Integer studentId, Integer lessonId, Boolean isAttended) throws SQLException {
       Connection connection = null;
       try {
           connection = getConnection();
           connection.setAutoCommit(false);

           try (PreparedStatement stmt = connection.prepareStatement(INSERT_INTO_ATTENDANCE_LOG_SCRIPT, Statement.RETURN_GENERATED_KEYS)) {
               stmt.setInt(1, studentId);
               stmt.setInt(2, lessonId);
               stmt.setBoolean(3, isAttended);

               int affectedRows = stmt.executeUpdate();
               if (affectedRows == 0) {
                   throw new SQLException("Creating attendance log failed, no rows affected.");
               }

               int attendanceLogId;
               try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                   if (generatedKeys.next()) {
                       attendanceLogId = generatedKeys.getInt(1);
                   } else {
                       throw new SQLException("Creating attendance log failed, no ID obtained.");
                   }
               }

               connection.commit();
               return attendanceLogId;
           }
       } catch (Exception e) {
           if (connection != null) {
               try {
                   connection.rollback();
               } catch (SQLException rollbackEx) {
                   e.addSuppressed(rollbackEx);
               }
           }

           throw e;
       } finally {
           if (connection != null) {
               try {
                   connection.close();
               } catch (SQLException e) {
                   e.printStackTrace();
               }
           }
       }
   }
}
