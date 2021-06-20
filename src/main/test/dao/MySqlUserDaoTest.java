package dao;

import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MySqlUserDaoTest {

    private List<User> testUsers;
    private UserDao userDao;


    @BeforeEach
    private void init() {

        userDao = new MySqlUserDao();
        testUsers = initUsers();
    }

    @Test
    public void testCreateUser_Successful() {

        User testUser = testUsers.get(2);

        userDao.createUser(testUser);

        User userById = userDao.getUserById(3).get();

        Assertions.assertNotNull(userById);
        Assertions.assertEquals(testUser, userById);

    }
    @Test
    public void testCreateUser_ThrowException() {

        User existedUser = testUsers.get(0);

        Assertions.assertThrows(Exception.class,() -> userDao.createUser(existedUser));
        Assertions.assertThrows(Exception.class,() -> userDao.createUser(null));

    }

    @Test
    public void testGetAllUsers() {

        List<User> users = userDao.getAllUsers();

        Assertions.assertEquals(testUsers, users);
        Assertions.assertSame(testUsers.size(), users.size());
        Assertions.assertFalse(users.isEmpty());

    }

    @Test
    public void testGetUserById_Successful() {

        User testUser = testUsers.get(2);

        User user = userDao.getUserById(3).orElse(null);

        Assertions.assertNotNull(user);
        Assertions.assertEquals(testUser, user);
        Assertions.assertSame(testUser.getId(), user.getId());

    }

    @Test
    public void testGetUserById_Null() {

        Assertions.assertNull(userDao.getUserById(5).orElse(null));

    }

    private List<User> initUsers() {
        return List.of(
                new User(1, "mykola", "paslavskyi",
                        LocalDate.of(2000, 2, 13)),
                new User(2, "ruslam", "dziub",
                        LocalDate.of(1999, 9, 8)),
                new User(3, "yura", "polan",
                        LocalDate.of(1995, 12,5))

        );
    }

}
