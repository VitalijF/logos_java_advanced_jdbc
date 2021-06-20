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

public class MySqlBlogDao implements BlogDao{

    private static Connection connection;
    private UserDao userDao;

    public MySqlBlogDao(UserDao userDao) {
        this.userDao = userDao;
    }

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
    public List<Blog> getAll() {
        List<Blog> blogs = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user_db.blogs");
            ResultSet result = preparedStatement.executeQuery()) {
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
    @Override
    @SneakyThrows
    public Blog getBlogById(int id) {

        try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                "select * from user_db.blogs where id = ?")) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {

                    int usedId = resultSet.getInt("user_id");
                    User userById = userDao.getUserById(usedId).orElse(null);

                    return new Blog(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            userById);

                }
            }
        }

        return null;
    }

    @Override
    @SneakyThrows
    public void createBlog(BlogInput blog) {

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into user_db.blogs(id, name, user_id) values (?,?,?)"
        )) {

            preparedStatement.setInt(1, blog.getId());
            preparedStatement.setString(2, blog.getName());
            preparedStatement.setInt(3, blog.getUserId());

            preparedStatement.execute();

        }

    }
}
