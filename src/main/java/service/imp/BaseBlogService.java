package service.imp;

import dao.BlogDao;
import dao.MySqlBlogDao;
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

    private BlogDao blogDao;

    public BaseBlogService(BlogDao blogDao) {
        this.blogDao = blogDao;
    }

    @Override
    @SneakyThrows
    public List<Blog> getAllBlogs() {
       return blogDao.getAllBlogs();
    }

    @Override
    @SneakyThrows
    public Blog getBlogById(int id) {
        Blog blogById = blogDao.getBlogById(id);
        if (blogById != null){
            return blogById;
        }
        return null;
    }

    @Override
    @SneakyThrows
    public void createBlog(BlogInput blog) {
        Blog blogById = blogDao.getBlogById(blog.getId());
        if(blogById != null){
            throw new RuntimeException("Already existed blog with such id in DB");
        }
        blogDao.createBlog(blog);
    }

}
