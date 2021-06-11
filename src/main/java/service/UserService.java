package service;

import exception.NoSuchBlogException;
import model.Blog;
import model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {

    void createUser(User user);

    User getUserById(int id) throws SQLException, NoSuchBlogException;

    List<User> getAllUsers();

}
