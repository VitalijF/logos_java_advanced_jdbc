package service;

import exception.DuplicateUserException;
import exception.NoSuchUserException;
import model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {

    void createUser(User user) throws SQLException, DuplicateUserException;

    User getUserById(int id) throws SQLException, NoSuchUserException;

    List<User> getAllUsers() throws SQLException;

    boolean isExistsUser(int id) throws SQLException;
}
