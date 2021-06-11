package service;

import exception.NoSuchBlogException;
import exception.UserNotFoundException;
import model.Blog;
import model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {

    void createUser(User user) throws SQLException;

    User getUserById(int id) throws SQLException, UserNotFoundException;

    List<User> getAllUsers() throws SQLException;

}
