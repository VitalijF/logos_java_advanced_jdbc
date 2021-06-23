package service.imp;

import dao.BlogDao;
import jdbc.MySqlConnector;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import model.Blog;
import model.BlogInput;
import org.apache.log4j.Logger;
import service.BlogService;
import service.UserService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
public class BaseBlogService implements BlogService {

    private static Connection connection;
    private BlogDao blogDao;
    public static final Logger LOGGER = Logger.getLogger(BaseBlogService.class);

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

        LOGGER.info("Getting all blogs from DB.");

        return blogDao.getAll();

    }

    @Override
    @SneakyThrows
    public Blog getBlogById(int id) {

        LOGGER.info(String.format("Getting blog with id {%d} from DB.", id));

        return blogDao.getBlogById(id);

    }

    @Override
    @SneakyThrows
    public void createBlog(BlogInput blogInput) {

        LOGGER.info(String.format("Creating blog {%s}.", blogInput));

        if (blogInput == null) {
            LOGGER.error(String.format("Blog user wants to create is null - %s", blogInput));
            throw new RuntimeException("Blog is null");
        }

        Blog blog = blogDao.getBlogById(blogInput.getId());

        if (blog != null) {
            LOGGER.error(String.format("Can't create blog %s, because it is already present in DB", blogInput));
            throw new RuntimeException("Blog already present in DB");
        }

        blogDao.createBlog(blogInput);

        LOGGER.info(String.format("Blog {%s} was created.", blogInput));

    }

}
