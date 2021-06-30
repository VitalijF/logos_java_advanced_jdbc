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

    private static final BlogService blogService = new BaseBlogService(new MySqlBlogDao());
    private static final UserService userService = new BaseUserService(new MySqlUserDao());

    public static void main(String[] args) throws SQLException, NoSuchBlogException, DuplicateBlogException {

//        User user = new User(
//                10,
//                "Sofiia",
//                "Didula",
//                LocalDate.of(1972, 2, 3)
//        );
        userService.getUserById(5);
//        System.out.println("Creating user successfully");
        BlogInput blog = new BlogInput(
                7,
                "Ubuntu 20.04",
                10
        );

        System.out.println("Creating Blog");
        blogService.createBlog(blog);
        System.out.println("Creating blog successfully");

        blogService.getAllBlogs();
        System.out.println("Return all Blogs");

        Blog blogById = blogService.getBlogById(blog.getId());
        System.out.println(blogById);

        System.out.println("Creating Blog");
        blogService.createBlog(blog);
        System.out.println("Creating blog successfully");
//        User user = new User(
//                2,
//                "Ivan",
//                "Stepanenko",
//                LocalDate.of(1972, 2, 3)
//        );

//        System.out.println("Creating Blog");
//        blogService.createBlog(blog);
//        System.out.println("Creating blog successfully");
//
//        Blog blogById = blogService.getBlogById(blog.getId());
//        System.out.println(blogById);

//        System.out.println("Creating user");
//        userService.createUser(user);
//        System.out.println("Creating user successfully");
//
//        User userById = userService.getUserById(user.getId());
//        System.out.println(userById);

    }
}
