package service.imp;

import exception.DuplicateBlogException;
import exception.NoSuchBlogException;
import exception.UserNotFoundException;
import jdbc.MySqlConnector;
import model.Blog;
import service.BlogService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlBlogService implements BlogService {

    private static Connection connection;

    static {
        try {
            connection = MySqlConnector.getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Blog> getAll() throws SQLException {
        List<Blog> blogs = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user_db.blogs");
             ResultSet result = preparedStatement.executeQuery()) {
            while (result.next()) {
                blogs.add(new Blog(result.getInt("id"), result.getString("name"), result.getInt("user_id")));
            }
            return blogs;
        }
    }

    @Override
    public Blog getBlogById(int id) throws SQLException, NoSuchBlogException {
        ResultSet result = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_db.blogs WHERE id = ?")) {
            statement.setInt(1, id);
            result = statement.executeQuery();
            if (result.next()) {
                return new Blog(result.getInt("id"), result.getString("name"), result.getInt("user_id"));
            } else throw new NoSuchBlogException("No blog with id : " + id);
        } finally {
            result.close();
        }
    }

    @Override
    public void createBlog(Blog blog) throws SQLException, DuplicateBlogException, UserNotFoundException {
        if (isExists(blog.getId())) {
            throw new DuplicateBlogException("Blog with id : " + blog.getId() + " already exists!");
        }

        //TODO:
        //Зробити перевірку чи є юзер з ідентифікатором blog.getUserId()

        if (!isUserExists(blog.getUserId())) {
            throw new UserNotFoundException("You want to create blog for non-existent user");
        }

        System.out.println("Creating blog with id : " + blog.getId());
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO user_db.blogs (id, name, user_id) VALUES (?, ?, ?)")) {
            statement.setInt(1, blog.getId());
            statement.setString(2, blog.getName());
            statement.setInt(3, blog.getUserId());
            statement.execute();
        }
    }


    private boolean isExists(int blogId) throws SQLException {
        boolean flag = false;
        for (Blog blog : getAll()) {
            flag = blog.getId() == blogId;
        }
        return flag;
    }

    private boolean isUserExists(int userId) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "select id from user_db.users where id = ?")){

            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            }

            return false;
        }
    }
}
