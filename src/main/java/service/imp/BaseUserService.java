package service.imp;

import dao.UserDao;
import lombok.SneakyThrows;
import model.User;
import org.apache.log4j.Logger;
import service.UserService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public class BaseUserService implements UserService {

    private static final Logger LOGGER = Logger.getLogger(BaseUserService.class);

    private UserDao userDao;

    public BaseUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void createUser(User user) {
        Optional<User> userById = userDao.getUserById(user.getId());
        if (userById.isPresent()) {
            LOGGER.error(String.format("Can NOT create user with id = {%d}, there is already exists user with such id " +
                    "in DB", user.getId()));
            throw new RuntimeException("User already present in DB");
        }
        LOGGER.info(String.format("User with id = {%d}, first_name = {%s}, last_name = {%s} has been created", user.getId(), user.getFirstName(), user.getLastName(), user.getAge()));
        userDao.createUser(user);
    }

    @Override
    @SneakyThrows
    public List<User> getAllUsers() {
        LOGGER.info("All users have been returned");
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
            LOGGER.info(String.format("User with id = {%d}, first_name = {%s}, last_name = {%s} has been returned", user.getId(), user.getFirstName(), user.getLastName(), user.getAge()));
            return user;
        }
        LOGGER.warn(String.format("User with id = {%d} does not exists", id));
        return null;
    }

}
