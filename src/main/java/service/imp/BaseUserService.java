package service.imp;

import dao.MySqlUserDao;
import dao.UserDao;
import jdbc.MySqlConnector;
import lombok.SneakyThrows;
import model.User;
import service.UserService;

import java.sql.*;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BaseUserService implements UserService {

    private UserDao userDao;

    public BaseUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void createUser(User user) {
        Optional<User> userById = userDao.getUserById(user.getId());
        if (userById.isPresent()) {
            throw new RuntimeException("User already present in DB");
        }

        userDao.createUser(user);
    }

    @Override
    @SneakyThrows
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    @SneakyThrows
    public User getUserById(int id) {
        Optional<User> userById = userDao.getUserById(id);
        if (userById.isPresent()) {
            User user = userById.get();
            LocalDate now = LocalDate.now();
            LocalDate dateOfBirth = user.getDateOfBirth();

            int yearsOld = (int) ChronoUnit.YEARS.between(dateOfBirth, now);
            user.setAge(yearsOld);

            return user;
        }

        return null;
    }

}
