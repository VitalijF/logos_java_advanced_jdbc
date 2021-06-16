package service.imp;

import exception.NoSuchBlogException;
import exception.UserNotFoundException;
import model.Blog;
import model.User;
import jdbc.MySqlConnector;
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
    public void createUser(User user) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO user_db.users (id, first_name, last_name, age) VALUES (?, ?, ?, ?)")) {
            statement.setInt(1, user.getId());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setInt(4, user.getAge());
            statement.execute();
        }
    }

    @Override
    //TODO: Implement
    public List<User> getAllUsers() throws SQLException {

        List<User> users = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from user_db.users;");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                users.add(new User(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getInt(4)));
            }
        }

        return users;
    }

    @Override
    //TODO: Implement
    public User getUserById(int id) throws SQLException, UserNotFoundException {

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from user_db.users where id = ?"
        )){

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                if (resultSet.next()) {
                    return new User(resultSet.getInt(1), resultSet.getString(2),
                            resultSet.getString(3), resultSet.getInt(4));
                }
            }

            throw new UserNotFoundException("You write wrong user_id");

        }
    }
}
