package service.impl;

import exeption.NoSuchUserException;
import jdbc.MySqlConnection;
import model.User;
import service.UserService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserService implements UserService {
  private static Connection connection;

  static {
    try {
      connection = MySqlConnection.getConnection();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<User> getAll() throws SQLException {
    List<User> users = new ArrayList<>();

    try (Statement statement = connection.createStatement();
         ResultSet result = statement.executeQuery("SELECT * FROM web_jdbc.user")) {
      while (result.next()) {
        users.add(new User(result.getInt("id"), result.getString("first_name"),
                result.getString("last_name"), result.getInt("age")));
      }
      return users;
    }
  }

  @Override
  public User getUserById(int id) throws SQLException, NoSuchUserException {
    ResultSet result = null;
    try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM web_jdbc.user WHERE id = ?")) {
      statement.setInt(1, id);
      result = statement.executeQuery();
      if (result.next()) {
        return new User(result.getInt("id"), result.getString("first_name"),
                result.getString("last_name"), result.getInt("age"));
      } else throw new NoSuchUserException("No user with id : " + id);
    } finally {
      result.close();
    }
  }

  @Override
  public void createUser(User user) throws SQLException {
    try (PreparedStatement statement = connection.prepareStatement("INSERT INTO web_jdbc.user (id, first_name, last_name, age) VALUES (?, ?, ?, ?)")) {
      statement.setInt(1, user.getId());
      statement.setString(2, user.getFirstName());
      statement.setString(3, user.getLastName());
      statement.setInt(4, user.getAge());
      statement.execute();
    }
  }
}
