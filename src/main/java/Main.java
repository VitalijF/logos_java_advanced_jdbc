import exception.DuplicateUserException;
import exception.NoSuchUserException;
import model.Blog;
import exception.DuplicateBlogException;
import exception.NoSuchBlogException;
import model.User;
import service.BlogService;
import service.UserService;
import service.imp.MySqlBlogService;
import service.imp.MySqlUserService;

import java.sql.SQLException;

public class Main {

    private static BlogService blogService = new MySqlBlogService();
    private static UserService userService = new MySqlUserService();

    public static void main(String[] args) throws SQLException, NoSuchBlogException, DuplicateBlogException,
            NoSuchUserException, DuplicateUserException {

        System.out.println("Creating new USER: id - 4; name - Tom Hanks; age - 45.");
        userService.createUser(new User(4, "Tom", "Hanks", 45));

        System.out.println("All users in DB:\n" + userService.getAllUsers());

        System.out.println("Get user by id (exists):\n" + userService.getUserById(3));
        System.out.println("Get user by id (doesn't exist):\n" + userService.getUserById(5));

        System.out.println("Creating new BLOG (user exists): id - 5; name - Cats; user_id - 4.");
        blogService.createBlog(new Blog(5, "Cats", 4));
        System.out.println("Creating new BLOG (user doesn't exist): id - 6; name - Pizza; user_id - 7.");
        blogService.createBlog(new Blog(6, "Pizza", 7));

        System.out.println("All blogs in DB:\n" + blogService.getAllBlogs());

        System.out.println("Get blog by id (exists):\n" + blogService.getBlogById(5));
        System.out.println("Get blog by id (doesn't exist):\n" + blogService.getBlogById(10));

    }
}
