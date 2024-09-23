package org.example.persistence.daos;

import org.example.persistence.entities.LessonEntity;

import java.sql.*;
import java.util.List;

public class LessonDao extends DaoBase {

    private static final String LESSON_ID_COLUMN_NAME = "id";
    private static final String DATE_COLUMN_NAME = "lesson_date";
    private static final String TABLE_NAME = "Lesson";

    private static final String SELECT_FROM_LESSON_SCRIPT = "SELECT * FROM " + TABLE_NAME;
    private static final String INSERT_INTO_LESSON_SCRIPT = "INSERT INTO " + TABLE_NAME + " (" + DATE_COLUMN_NAME + ") VALUES (?)";

    public LessonDao(String url, String userName, String password) {
        super(url, userName, password);
    }

    public List<LessonEntity> getLessons() throws SQLException {
        return getList(SELECT_FROM_LESSON_SCRIPT, result -> {
            int lessonId = result.getInt(LESSON_ID_COLUMN_NAME);
            Long date = result.getLong(DATE_COLUMN_NAME);
            return new LessonEntity(lessonId, date);
        });
    }

    public int createLesson(Long date) throws SQLException {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            try (PreparedStatement stmt = connection.prepareStatement(INSERT_INTO_LESSON_SCRIPT, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setLong(1, date);

                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Creating lesson failed, no rows affected.");
                }

                int lessonId;
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        lessonId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating lesson failed, no ID obtained.");
                    }
                }

                connection.commit();
                return lessonId;
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
