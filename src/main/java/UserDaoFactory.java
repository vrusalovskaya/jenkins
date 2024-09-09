public class UserDaoFactory {

    private static final String URL = "jdbc:postgresql://192.168.100.8:5432/testdb";
    private static final String USER = "cent";
    private static final String PASSWORD = "victoria123";

    public static UserDao createUserDao() {
        return new UserDao(URL, USER, PASSWORD);
    }
}
