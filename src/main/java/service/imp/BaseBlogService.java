package service.imp;

import dao.BlogDao;
import dao.MySqlBlogDao;
import dao.MySqlUserDao;
import jdbc.MySqlConnector;
import lombok.SneakyThrows;
import model.Blog;
import model.BlogInput;
import org.apache.log4j.Logger;
import service.BlogService;
import service.UserService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BaseBlogService implements BlogService {

    private static final Logger LOGGER = Logger.getLogger(BaseUserService.class);

    private BlogDao blogDao;

    public BaseBlogService(BlogDao blogDao) {
        this.blogDao = blogDao;
    }

    @Override
    @SneakyThrows
    public List<Blog> getAllBlogs() {
        LOGGER.info("All blogs have been returned");
        return blogDao.getAllBlogs();
    }

    @Override
    @SneakyThrows
    public Blog getBlogById(int id) {
        Blog blogById = blogDao.getBlogById(id);
        if (blogById != null){
            LOGGER.info(String.format("Blog with id = {%d}, name = {%s} and User data - id ={%d}, " +
                    "first_name = {%s}, last_name = {%s} has been returned", blogById.getId(), blogById.getName(),
                    blogById.getUser().getId(), blogById.getUser().getFirstName(), blogById.getUser().getLastName() ));
            return blogById;
        }
        LOGGER.info(String.format("Blog with id = {%d} does not exists", blogById.getId()));
        return null;
    }

    @Override
    @SneakyThrows
    public void createBlog(BlogInput blog) {
        Blog blogById = blogDao.getBlogById(blog.getId());
        if(blogById != null){
            LOGGER.error(String.format("Can NOT create blog with id = {%d}, there is already exists blog with such id " +
                    "in DB", blogById.getId()));
            throw new RuntimeException("Already existed blog with such id in DB");
        }
        LOGGER.info(String.format("Blog with id = {%d}, name = {%s} and user_id ={%d} has been created",
                blog.getId(), blog.getName(), blog.getUserId()));
        blogDao.createBlog(blog);
    }

}
