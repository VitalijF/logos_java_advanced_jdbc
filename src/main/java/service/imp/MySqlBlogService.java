package service.imp;

import exception.DuplicateBlogException;
import exception.NoSuchBlogException;
import exception.NoSuchUserException;
import jdbc.MySqlConnector;
import model.Blog;
import service.BlogService;
import service.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlBlogService implements BlogService {

    private static Connection connection;
    private static UserService userService = new MySqlUserService();

    static {
        try {
            connection = MySqlConnector.getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Blog> getAllBlogs() throws SQLException {
        List<Blog> blogs = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM web.blogs");
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                blogs.add(new Blog(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getInt("user_id")));
            }
            return blogs;
        }
    }

    @Override
    public Blog getBlogById(int id) throws SQLException, NoSuchBlogException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM web.blogs WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return new Blog(
                            result.getInt("id"),
                            result.getString("name"),
                            result.getInt("user_id"));
                }
                throw new NoSuchBlogException("No blog with id : " + id);
            }
        }
    }

    @Override
    public void createBlog(Blog blog) throws SQLException, DuplicateBlogException, NoSuchUserException {
        if (isExistsBlog(blog.getId())) {
            throw new DuplicateBlogException("Blog with id : " + blog.getId() + " already exists!");
        }

        //TODO:
        //Зробити перевірку чи є юзер з ідентифікатором blog.getUserId()
            if (userService.isExistsUser(blog.getUserId())) {
                try (PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO web.blogs (id, name, user_id) VALUES (?, ?, ?)")) {
                    statement.setInt(1, blog.getId());
                    statement.setString(2, blog.getName());
                    statement.setInt(3, blog.getUserId());
                    statement.execute();
                }
            } else throw new NoSuchUserException("No user with id : " + blog.getUserId());
        }

    private boolean isExistsBlog(int blogId) throws SQLException {
        boolean flag = false;
        for (Blog blog : getAllBlogs()) {
            flag = blog.getId() == blogId;
        }
        return flag;
    }
}
