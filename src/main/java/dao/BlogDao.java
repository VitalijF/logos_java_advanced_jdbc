package dao;

import exception.DuplicateBlogException;
import exception.NoSuchBlogException;
import jdbc.MySqlConnector;
import lombok.SneakyThrows;
import model.Blog;
import model.BlogInput;
import model.User;
import service.UserService;
import service.imp.BaseUserService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface BlogDao {

    List<Blog> getAll();
    Blog getBlogById(int id);
    void createBlog(BlogInput blog);

}
