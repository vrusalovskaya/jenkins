package org.example.persistence.daos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class DaoBase {

    private final String url;
    private final String userName;
    private final String password;

    public DaoBase(String url, String userName, String password) {
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    protected <T> List<T> getList(String query, ResultReader<T> resultReader) throws SQLException {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet result = statement.executeQuery(query)) {
                ArrayList<T> entities = new ArrayList<>();
                while (result.next()) {
                    entities.add(resultReader.read(result));
                }

                return entities;
            }
        }
    }

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, userName, password);
    }

    protected interface ResultReader<T> {
        T read(ResultSet resultSet) throws SQLException;
    }
}
