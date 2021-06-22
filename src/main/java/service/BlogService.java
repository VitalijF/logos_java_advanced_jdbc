package service;

import exception.NoSuchUserException;
import model.Blog;
import exception.DuplicateBlogException;
import exception.NoSuchBlogException;

import java.sql.SQLException;
import java.util.List;

public interface BlogService {

    List<Blog> getAllBlogs() throws SQLException;

    Blog getBlogById(int id) throws SQLException, NoSuchBlogException;

    void createBlog(Blog blog) throws SQLException, NoSuchBlogException, DuplicateBlogException, NoSuchUserException;
}
