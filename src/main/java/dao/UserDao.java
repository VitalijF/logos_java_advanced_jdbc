package dao;

import model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    void createUser(User user);

    List<User> getAllUsers();

    Optional<User> getUserById(int id);
}
