package service;

import model.Blog;
import exception.DuplicateBlogException;
import exception.NoSuchBlogException;
import model.BlogInput;

import java.sql.SQLException;
import java.util.List;

public interface BlogService {

    List<Blog> getAllBlogs();

    Blog getBlogById(int id);

    void createBlog(BlogInput blog);
}
