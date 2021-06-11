import dao.MySqlUserDao;
import model.Blog;
import exception.DuplicateBlogException;
import exception.NoSuchBlogException;
import model.User;
import service.BlogService;
import service.UserService;
import service.imp.MySqlBlogService;
import service.imp.BaseUserService;

import java.sql.SQLException;
import java.time.LocalDate;

public class Main {

    private static BlogService blogService = new MySqlBlogService();
    private static UserService userService = new BaseUserService(new MySqlUserDao());

    public static void main(String[] args) throws SQLException, NoSuchBlogException, DuplicateBlogException {

        User user = new User(
                2,
                "Ivan",
                "Stepanenko",
                LocalDate.of(1972, 2, 3)
        );

        System.out.println("Creating user");
        userService.createUser(user);
        System.out.println("Creating user successfully");

        User userById = userService.getUserById(user.getId());
        System.out.println(userById);

    }
}
