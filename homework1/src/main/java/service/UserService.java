package service;

import exception.DublicateUserException;
import exception.NoSuchUserException;
import model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {

    List<User> getAll() throws SQLException;

    User getUserById(int id) throws SQLException, NoSuchUserException;

    void createUser(User user) throws SQLException, NoSuchUserException, DublicateUserException;
}
