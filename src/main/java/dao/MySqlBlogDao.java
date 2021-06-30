package dao;

import jdbc.MySqlConnector;
import lombok.SneakyThrows;
import model.Blog;
import model.BlogInput;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySqlBlogDao implements BlogDao{

    private static final Connection connection;
    private final UserDao userDao = new MySqlUserDao();

    static {
        try {
            connection = MySqlConnector.getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    @SneakyThrows
    public List<Blog> getAllBlogs() {
        List<Blog> blogs = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM web_jdbc.blogs");
             ResultSet result = statement.executeQuery()) {
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

    @Override
    @SneakyThrows
    public Blog getBlogById(int id) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM web_jdbc.blogs WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    int userId = result.getInt("user_id");
                    Optional<User> userById = userDao.getUserById(userId);
                    return new Blog(
                            result.getInt("id"),
                            result.getString("name"),
                            userById.orElse(null));
                } else return null;
            }
        }
    }

    @Override
    @SneakyThrows
    public void createBlog(BlogInput blog) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO web_jdbc.blogs (id, name, user_id) VALUES (?, ?, ?)")) {
            statement.setInt(1, blog.getId());
            statement.setString(2, blog.getName());
            statement.setInt(3, blog.getUserId());
            statement.execute();
        }
    }


}
