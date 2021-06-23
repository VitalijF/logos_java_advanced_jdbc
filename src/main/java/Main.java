import dao.MySqlBlogDao;
import dao.MySqlUserDao;
import model.Blog;
import exception.DuplicateBlogException;
import exception.NoSuchBlogException;
import model.User;
import service.BlogService;
import service.UserService;
import service.imp.BaseBlogService;
import service.imp.BaseUserService;

import java.sql.SQLException;
import java.time.LocalDate;

public class Main {

    private static BlogService blogService = new BaseBlogService(new MySqlBlogDao(new MySqlUserDao()));
    private static UserService userService = new BaseUserService(new MySqlUserDao());

    public static void main(String[] args) throws SQLException, NoSuchBlogException, DuplicateBlogException {
        

    }
}
