import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private static final String SQL_SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String SQL_INSERT_USER = "INSERT INTO users (first_name, last_name, age) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE_USER = "UPDATE users SET first_name = ?, last_name = ?, age = ? WHERE id = ?";
    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE id = ?";

    private final String url;
    private final String userName;
    private final String password;

    public UserDao(String url, String userName, String password) {
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    public List<User> getUsers() throws SQLException {
        List<User> users = new ArrayList<>();

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL_USERS)) {
            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("age"));
                users.add(user);
            }
        }

        return users;
    }

    public int createUser(String firstName, String lastName, int age) throws SQLException {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            try (PreparedStatement stmt = connection.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, firstName);
                stmt.setString(2, lastName);
                stmt.setInt(3, age);

                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                int userId;
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        userId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }

                connection.commit();
                return userId;
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

    public void updateUser(User user) throws SQLException {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            try (PreparedStatement stmt = connection.prepareStatement(SQL_UPDATE_USER)) {
                stmt.setString(1, user.getFirstName());
                stmt.setString(2, user.getLastName());
                stmt.setInt(3, user.getAge());
                stmt.setInt(4, user.getId());
                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Updating user failed, no rows affected.");
                }

                connection.commit();
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

    public void deleteUser(int id) throws SQLException {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            try (PreparedStatement stmt = connection.prepareStatement(SQL_DELETE_USER)) {
                stmt.setInt(1, id);
                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Deleting user failed, no rows affected.");
                }

                connection.commit();
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

    private Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(url, userName, password);
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        return connection;
    }
}
