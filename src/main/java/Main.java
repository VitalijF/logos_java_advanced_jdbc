import dao.MySqlBlogDao;
import dao.MySqlUserDao;
import model.Blog;
import exception.DuplicateBlogException;
import exception.NoSuchBlogException;
import model.BlogInput;
import model.User;
import service.BlogService;
import service.UserService;
import service.imp.BaseBlogService;
import service.imp.BaseUserService;

import java.sql.SQLException;
import java.time.LocalDate;

public class Main {

    private static BlogService blogService = new BaseBlogService(new MySqlBlogDao());
    private static UserService userService = new BaseUserService(new MySqlUserDao());

    public static void main(String[] args) throws SQLException, NoSuchBlogException, DuplicateBlogException {

        BlogInput blog = new BlogInput(
                3,
                "Windows11",
                2
        );
//        User user = new User(
//                2,
//                "Ivan",
//                "Stepanenko",
//                LocalDate.of(1972, 2, 3)
//        );

        System.out.println("Creating Blog");
        blogService.createBlog(blog);
        System.out.println("Creating blog successfully");

        Blog blogById = blogService.getBlogById(blog.getId());
        System.out.println(blogById);

//        System.out.println("Creating user");
//        userService.createUser(user);
//        System.out.println("Creating user successfully");
//
//        User userById = userService.getUserById(user.getId());
//        System.out.println(userById);

    }
}
