import exeption.DuplicateBlogException;
import exeption.DuplicateUserException;
import exeption.NoSuchBlogException;
import exeption.NoSuchUserException;
import model.Blog;
import model.User;
import service.BlogService;
import service.UserService;
import service.impl.MySqlBlogService;
import service.impl.MySqlUserService;

import java.sql.SQLException;

public class Main {
  private static BlogService blogService = new MySqlBlogService();
  private static UserService userService = new MySqlUserService();
  public static void main(String[] args) throws SQLException, NoSuchBlogException, DuplicateBlogException,
          NoSuchUserException, DuplicateUserException {

//        System.out.println(" ----------- GET all ---------------- ");
//        userService.getAll().forEach(System.out::println);
//
//        System.out.println(" ------------- GET by ID ----------------");
//        System.out.println(userService.getUserById(6));

//        System.out.println(" ---------- CREATE User --------------");
//        userService.createUser(new User(6, "Sofiia", "Didula", 19));

    System.out.println(" ---------- CREATE Blog with User --------------");
    blogService.createBlog(new Blog(7, "Window 8", 7));

//    System.out.println(" ------------- GET by ID ----------------");
//    System.out.println(blogService.getBlogById(7));

  }
}
