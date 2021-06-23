package service.imp;

import dao.UserDao;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import service.UserService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BaseUserServiceTest {

    private UserDao userDao;

    private UserService baseUserService;

    private static List<User> USERS = initUsers();


    @BeforeEach
    public void init() {
        userDao = Mockito.mock(UserDao.class);
        baseUserService = new BaseUserService(userDao);
    }

    @Test
    void testAllGetUsers() {
        List<User> expectedUsersFromDB = initUsers();
        Mockito.when(userDao.getAllUsers()).thenReturn(expectedUsersFromDB);

        List<User> allUsers = baseUserService.getAllUsers();

        Assertions.assertNotNull(allUsers);
        Assertions.assertSame(2, allUsers.size());
        Assertions.assertEquals(expectedUsersFromDB, allUsers);
    }

    @Test
    void testCreateUserExceptionThrow() {
        User user = USERS.get(1);
        Mockito.when(userDao.getUserById(2)).thenReturn(Optional.of(user));
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> baseUserService.createUser(user)
        );


        Mockito.verify(userDao, Mockito.times(1))
                .getUserById(2);
        Mockito.verify(userDao, Mockito.never())
                .createUser(user);
        Assertions.assertEquals("User already present in DB", exception.getMessage());
    }

    @Test
    void testCreateUserSuccessfully() {
        User user = USERS.get(1);
        Mockito.when(userDao.getUserById(2)).thenReturn(Optional.empty());

        baseUserService.createUser(user);

        Mockito.verify(userDao, Mockito.times(1))
                .getUserById(2);
        Mockito.verify(userDao, Mockito.times(1))
                .createUser(user);
    }

    @Test
    void testGetUserById_ReturnNull() {

        Mockito.when(userDao.getUserById(1)).thenReturn(Optional.empty());

        User user = baseUserService.getUserById(1);

        Assertions.assertNull(user);

    }

    @Test
    void testGetUserById_Successfully() {

        User testUser = USERS.get(0);

        Mockito.when(userDao.getUserById(1)).thenReturn(Optional.of(testUser));

        User user = baseUserService.getUserById(1);

        Assertions.assertNotNull(user);
        Assertions.assertSame(testUser.getId(), user.getId());
        Assertions.assertSame(24, user.getAge());

    }



    private static List<User> initUsers() {
        return List.of(
                new User(1,
                        "firstName",
                        "lastName",
                        LocalDate.of(1996,7,2)),
                new User(2,
                        "firstName2",
                        "lastName3",
                        LocalDate.of(1996,7,2))
        );
    }
}
