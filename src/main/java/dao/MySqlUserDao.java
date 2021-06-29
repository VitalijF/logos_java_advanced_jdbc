package dao;

import jdbc.MySqlConnector;
import lombok.SneakyThrows;
import model.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySqlUserDao implements UserDao {

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
    @SneakyThrows
    public void createUser(User user) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO web_jdbc.users (id, first_name, last_name, birth_date) VALUES (?, ?, ?, ?)")) {
            statement.setInt(1, user.getId());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setDate(4, java.sql.Date.valueOf(user.getDateOfBirth()));
            statement.execute();
        }
    }

    @Override
    @SneakyThrows
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM web_jdbc.users");
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                users.add(buildUserFromResultSet(result));
            }
            return users;
        }
    }

    @Override
    @SneakyThrows
    public Optional<User> getUserById(int id) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM web_jdbc.users WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return Optional.of(buildUserFromResultSet(result));
                }
                return Optional.empty();
            }
        }
    }

    private LocalDate convertFromSQLDate(Date dateToConvert) {
        return dateToConvert.toLocalDate();
    }

    private User buildUserFromResultSet(ResultSet result) throws SQLException {
        return new User(
                result.getInt("id"),
                result.getString("first_name"),
                result.getString("last_name"),
                convertFromSQLDate(result.getDate("birth_date")));
    }
}
