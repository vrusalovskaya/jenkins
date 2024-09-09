import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private final UserDao userDao = UserDaoFactory.createUserDao();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new ServletException("PostgreSQL driver not found", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<User> users = userDao.getUsers();

            setJsonResponseHeaders(response);

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), users);
        } catch (SQLException e) {
            throw new ServletException("Error retrieving users", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(request.getInputStream(), User.class);

            int userId = userDao.createUser(user.getFirstName(), user.getLastName(), user.getAge());
            user.setId(userId);

            setJsonResponseHeaders(response);

            mapper.writeValue(response.getOutputStream(), user);
        } catch (SQLException e) {
            throw new ServletException("Error creating user", e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(request.getInputStream(), User.class);

            userDao.updateUser(user);

            setJsonResponseHeaders(response);

            mapper.writeValue(response.getOutputStream(), user);
        } catch (SQLException e) {
            throw new ServletException("Error updating user", e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));

            userDao.deleteUser(id);

            setJsonResponseHeaders(response);

            response.getWriter().write("{\"status\":\"success\"}");
        } catch (SQLException e) {
            throw new ServletException("Error connecting to database", e);
        }
    }

    private void setJsonResponseHeaders(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }
}
