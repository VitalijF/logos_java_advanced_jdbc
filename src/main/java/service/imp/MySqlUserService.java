package service.imp;

import exception.DuplicateUserException;
import exception.NoSuchUserException;
import jdbc.MySqlConnector;
import model.User;
import service.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserService implements UserService {

    private static Connection connection;

    static {
        try {
            connection = MySqlConnector.getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM web.users");
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                users.add(new User(
                        result.getInt("id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getInt("age")));
            }
            return users;
        }
    }

    @Override
    public User getUserById(int id) throws SQLException, NoSuchUserException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM web.users WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return new User(
                            result.getInt("id"),
                            result.getString("first_name"),
                            result.getString("last_name"),
                            result.getInt("age"));
                }
                throw new NoSuchUserException("No user with id : " + id);
            }
        }
    }

    @Override
    public void createUser(User user) throws SQLException, DuplicateUserException {
        if (isExistsUser(user.getId())) {
            throw new DuplicateUserException("User with id : " + user.getId() + " already exists!");
        }
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO web.users (id, first_name, last_name, age) VALUES (?, ?, ?, ?)")) {
            statement.setInt(1, user.getId());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setInt(4, user.getAge());
            statement.execute();
        }
    }

    @Override
    public boolean isExistsUser(int userId) throws SQLException {
        boolean flag = false;
        for (User user : getAllUsers()) {
            flag = user.getId() == userId;
        }
        return flag;
    }
}
