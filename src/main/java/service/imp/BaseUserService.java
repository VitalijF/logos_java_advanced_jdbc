package service.imp;

import dao.MySqlUserDao;
import dao.UserDao;
import jdbc.MySqlConnector;
import lombok.SneakyThrows;
import model.User;
import org.apache.log4j.Logger;
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
    public static final Logger LOGGER = Logger.getLogger(BaseUserService.class);

    public BaseUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void createUser(User user) {

        LOGGER.info(String.format("Creating user {%s}.", user));
        Optional<User> userById = userDao.getUserById(user.getId());

        if (userById.isPresent()) {
            LOGGER.error(String.format("Can't create user {%s} because he is already present in DB.", user));

            throw new RuntimeException("User already present in DB");
        }

        userDao.createUser(user);

        LOGGER.info(String.format("User {%s} was created.", user));

    }

    @Override
    @SneakyThrows
    public List<User> getAllUsers() {

        LOGGER.info("Getting all users from DB.");

        return userDao.getAllUsers();
    }

    @Override
    @SneakyThrows
    public User getUserById(int id) {

        LOGGER.info(String.format("Getting user by id {%d}.", id));
        Optional<User> userById = userDao.getUserById(id);
        if (userById.isPresent()) {
            User user = userById.get();
            LocalDate now = LocalDate.now();
            LocalDate dateOfBirth = user.getDateOfBirth();

            int yearsOld = (int) ChronoUnit.YEARS.between(dateOfBirth, now);
            user.setAge(yearsOld);

            LOGGER.info(String.format("User by id {%d} was found - {%s}.", id, user));

            return user;
        }

        LOGGER.warn(String.format("Can't found user with id {%d}, return null", id));

        return null;
    }

}
