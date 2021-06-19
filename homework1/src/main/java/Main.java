import exception.DublicateBlogException;
import exception.DublicateUserException;
import exception.NoSuchBlogException;
import exception.NoSuchUserException;
import model.Blog;
import model.User;
import service.BlogService;
import service.UserService;
import service.imp.MySqlBlogService;
import service.imp.MySqlUserService;

import java.sql.SQLException;

public class Main {

    private static BlogService blogService = new MySqlBlogService();
    private static UserService userService = new MySqlUserService();

    public static void main(String[] args) throws SQLException, NoSuchBlogException, DublicateBlogException, NoSuchUserException, DublicateUserException {

//        System.out.println(" ----------- GET all blogs ---------------- ");
//        blogService.getAll().forEach(System.out::println);

//        System.out.println(" ------------- GET blog by ID ----------------");
//        System.out.println(blogService.getBlogById(331));

//        System.out.println(" ---------- CREATE Blog --------------");
//        blogService.createBlog(new Blog(555, "blog_5", 5));

//        System.out.println(" ----------- GET all users ---------------- ");
//        userService.getAll().forEach(System.out::println);

//        System.out.println(" ------------- GET user by ID ----------------");
//        System.out.println(userService.getUserById(1));

//        System.out.println(" ---------- CREATE Blog --------------");
//        userService.createUser(new User(5, "first_name_5", "last_name_5", 55));
    }
}
