package dao;

import exception.DuplicateBlogException;
import exception.NoSuchBlogException;
import jdbc.MySqlConnector;
import lombok.SneakyThrows;
import model.Blog;
import model.BlogInput;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySqlBlogDao {

    private static Connection connection;
    private UserDao userDao = new MySqlUserDao();

    static {
        try {
            connection = MySqlConnector.getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public List<Blog> getAll() {
        List<Blog> blogs = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery("SELECT * FROM web.blogs")) {
            while (result.next()) {
                int userId = result.getInt("user_id");
                Optional<User> userById = userDao.getUserById(userId);
                blogs.add(new Blog(
                        result.getInt("id"),
                        result.getString("name"),
                        userById.orElse(null)));
            }
            return blogs;
        }
    }

    //TODO: Implement
    @SneakyThrows
    public Blog getBlogById(int id) {
        return null;
    }

    @SneakyThrows
    public void createBlog(BlogInput blog) {

    }


}
