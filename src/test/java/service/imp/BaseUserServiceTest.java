package service.imp;

import dao.UserDao;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BaseUserServiceTest {

    private UserDao userDao;

    private BaseUserService baseUserService;

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
    void testGetUserByIdSuccessful(){
        User user = USERS.get(1);
        Mockito.when(userDao.getUserById(2)).thenReturn(Optional.of(user));

        User thisUser = baseUserService.getUserById(2);

        Assertions.assertNotNull(thisUser);
        Assertions.assertEquals(2, thisUser.getId());
    }

    @Test
    void testGetUserByIdFailed(){
        Mockito.when(userDao.getUserById(2)).thenReturn(Optional.empty());

        User thisUser = baseUserService.getUserById(2);

        Assertions.assertNull(thisUser);
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
