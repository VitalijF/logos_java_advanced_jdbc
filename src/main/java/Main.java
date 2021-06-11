import exception.UserNotFoundException;
import model.Blog;
import exception.DuplicateBlogException;
import exception.NoSuchBlogException;
import service.BlogService;
import service.UserService;
import service.imp.MySqlBlogService;
import service.imp.MySqlUserService;

import java.sql.SQLException;

public class Main {

    private static BlogService blogService = new MySqlBlogService();
    private static UserService userService = new MySqlUserService();

    public static void main(String[] args) throws SQLException, NoSuchBlogException, DuplicateBlogException {

        userService.getAllUsers().forEach(System.out::println);

        try {
            System.out.println(userService.getUserById(1));
            System.out.println(userService.getUserById(2));
            System.out.println(userService.getUserById(3));
            System.out.println(userService.getUserById(4));
            System.out.println(userService.getUserById(1));
            System.out.println(userService.getUserById(5));
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            blogService.createBlog(new Blog(3, "Life", 2));
            System.out.println(blogService.getBlogById(2));
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }


    }
}
