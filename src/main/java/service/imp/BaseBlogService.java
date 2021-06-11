package service.imp;

import dao.MySqlUserDao;
import jdbc.MySqlConnector;
import lombok.SneakyThrows;
import model.Blog;
import model.BlogInput;
import service.BlogService;
import service.UserService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BaseBlogService implements BlogService {

    private static Connection connection;
    private UserService userService = new BaseUserService(new MySqlUserDao());

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
       return null;
    }

    @Override
    @SneakyThrows
    public Blog getBlogById(int id) {
        return null;
    }

    @Override
    @SneakyThrows
    public void createBlog(BlogInput blog) {

    }

}
