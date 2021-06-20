package service.imp;

import dao.BlogDao;
import jdbc.MySqlConnector;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import model.Blog;
import model.BlogInput;
import service.BlogService;
import service.UserService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
public class BaseBlogService implements BlogService {

    private static Connection connection;
    private BlogDao blogDao;

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

        return blogDao.getAll();

    }

    @Override
    @SneakyThrows
    public Blog getBlogById(int id) {

        return blogDao.getBlogById(id);

    }

    @Override
    @SneakyThrows
    public void createBlog(BlogInput blogInput) {

        if (blogInput == null) {
            throw new RuntimeException("Blog is null");
        }

        Blog blog = blogDao.getBlogById(blogInput.getId());

        if (blog != null) {
            throw new RuntimeException("Blog already present in DB");
        }

        blogDao.createBlog(blogInput);

    }

}
