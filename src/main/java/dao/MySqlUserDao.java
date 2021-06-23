package dao;

import jdbc.MySqlConnector;
import lombok.SneakyThrows;
import model.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySqlUserDao implements UserDao {

    private static Connection connection;
    public static final Logger LOGGER = Logger.getLogger(MySqlUserDao.class);

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

        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO user_db.users (id, first_name, last_name, birth_date) VALUES (?, ?, ?, ?)")) {

            LOGGER.debug(String.format("Creating user {%s}, query = %s", user, statement));
            statement.setInt(1, user.getId());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setDate(4, java.sql.Date.valueOf(user.getDateOfBirth()));
            statement.execute();

            LOGGER.info(String.format("User {%s} was created.", user));
        }
    }

    @Override
    @SneakyThrows
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_db.users");
             ResultSet result = statement.executeQuery()) {

            LOGGER.debug(String.format("Getting all users from DB, query = %s", statement));

            while (result.next()) {
                users.add(buildUserFromResultSet(result));
            }
            LOGGER.info("Returning all users.");
            return users;
        }
    }

    @Override
    @SneakyThrows
    public Optional<User> getUserById(int id) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_db.users WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {

                LOGGER.debug(String.format("Get user by id {%d} from DB, query = %s", id, statement));

                if (result.next()) {
                    return Optional.of(buildUserFromResultSet(result));
                }

                LOGGER.warn(String.format("Can't found user with id {%d}", id));
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
