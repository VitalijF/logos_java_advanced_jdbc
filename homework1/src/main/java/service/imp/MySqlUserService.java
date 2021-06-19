package service.imp;

import exception.DublicateUserException;
import exception.NoSuchUserException;
import jdbc.MySqlConnector;
import model.User;
import service.UserService;
import java.sql.*;
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
    public List<User> getAll() throws SQLException {
        List<User> users = new ArrayList<>();

        ResultSet result = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM web.users")) {
            result = statement.executeQuery();
            while (result.next()) {
                users.add(new User(result.getInt("id"), result.getString("firstName"), result.getString("lastName"), result.getInt("age")));
            }
            return users;
        } finally {
            result.close();
        }
    }

    @Override
    public User getUserById(int id) throws SQLException, NoSuchUserException {
        ResultSet result = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM web.users WHERE id = ?")) {
            statement.setInt(1, id);
            result = statement.executeQuery();
            if (result.next()) {
                return new User(result.getInt("id"), result.getString("firstName"), result.getString("lastName"), result.getInt("age"));
            } else throw new NoSuchUserException("No user with id : " + id);
        } finally {
            result.close();
        }
    }


    @Override
    public void createUser(User user) throws SQLException, DublicateUserException {
        if(ifExists(user.getId())){
            throw new DublicateUserException("User with id : " + user.getId() + " already exists!");
        } else {
            System.out.println("Creating user with id : " + user.getId());
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO web.users (id, firstName, lastName, age) VALUES (?, ?, ?, ?)")) {
                statement.setInt(1,user.getId());
                statement.setString(2, user.getFirstName());
                statement.setString(3, user.getLastName());
                statement.setInt(4, user.getAge());
                statement.execute();
            }
        }
    }

    private boolean ifExists(int userId) throws SQLException {
        boolean flag = false;
        for (User user : getAll()) {
            flag = user.getId() == userId;
        }
        return flag;
    }
}
