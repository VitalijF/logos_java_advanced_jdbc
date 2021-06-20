package dao;

import model.Blog;
import model.BlogInput;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MySqlBlogDaoTest {


    private List<Blog> testBlogs;
    private UserDao userDao;
    private BlogDao blogDao;

    @BeforeEach
    public void init() {

        userDao = Mockito.mock(UserDao.class);
        blogDao = new MySqlBlogDao(userDao);
        testBlogs = initBlogs();
    }

    @Test
    public void testGetAllBlogsSuccessful() {

        User user1 = testBlogs.get(0).getUser();
        User user2 = testBlogs.get(1).getUser();

        Mockito.when(userDao.getUserById(1)).thenReturn(Optional.of(user1));
        Mockito.when(userDao.getUserById(2)).thenReturn(Optional.of(user2));

        List<Blog> blogs = blogDao.getAll();

        Assertions.assertSame(2, blogs.size());
        Assertions.assertEquals(testBlogs, blogs);

    }

    @Test
    public void testGetAllBlogs_UserNull() {

        Mockito.when(userDao.getUserById(Mockito.anyInt())).thenReturn(Optional.empty());

        List<Blog> blogs = blogDao.getAll();

        Assertions.assertNotEquals(testBlogs, blogs);
        Assertions.assertNull(blogs.get(0).getUser());

    }

    @Test
    public void testGetBlogById_Successful() {

        int id = 1;
        Blog blog = testBlogs.get(0);

        Mockito.when(userDao.getUserById(id)).thenReturn(Optional.of(blog.getUser()));

        Blog blogById = blogDao.getBlogById(id);

        Assertions.assertNotNull(blogById);
        Assertions.assertEquals(blog, blogById);

    }
    @Test
    public void testGetBlogById_UserNull() {

        int id = 1;

        Mockito.when(userDao.getUserById(1)).thenReturn(Optional.empty());

        Blog blogById = blogDao.getBlogById(id);

        Assertions.assertNotNull(blogById);
        Assertions.assertNull(blogById.getUser());

    }
    @Test
    public void testGetBlogById_BlogNull() {

        int id = 4;

        Mockito.when(userDao.getUserById(Mockito.anyInt())).thenReturn(Optional.empty());

        Blog blogById = blogDao.getBlogById(id);

        Assertions.assertNull(blogById);

    }

    @Test
    public void testCreateBlog_Successful() {

        BlogInput blogInput = new BlogInput(2, "Music", 2);

        Blog testBlog = testBlogs.get(1);

        blogDao.createBlog(blogInput);

        Mockito.when(userDao.getUserById(2)).thenReturn(Optional.of(testBlog.getUser()));

        Blog blogById = blogDao.getBlogById(2);

        Assertions.assertNotNull(blogById);
        Assertions.assertEquals(testBlog, blogById);

    }

    @Test
    public void testCreateBlog_Exception() {

        Assertions.assertThrows(Exception.class, () ->
                blogDao.createBlog(new BlogInput(1, "name", 3)));
        Assertions.assertThrows(Exception.class, () ->
                blogDao.createBlog(new BlogInput(3, "name", 5)));
        Assertions.assertThrows(Exception.class, () ->
                blogDao.createBlog(null));

    }



    private List<Blog> initBlogs () {

        return List.of(
                new Blog(1, "Nature", new User(
                1, "mykola", "paslavskyi", LocalDate.of(2000, 2, 13))),
                new Blog(2, "Music", new User(
                        2, "ruslam", "dziub", LocalDate.of(1999, 9, 8)))
        );

    }





}
