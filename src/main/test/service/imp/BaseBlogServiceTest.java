package service.imp;

import dao.BlogDao;
import dao.MySqlBlogDao;
import model.Blog;
import model.BlogInput;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import service.BlogService;

import java.time.LocalDate;
import java.util.List;

public class BaseBlogServiceTest {

    private BlogDao blogDao;
    private BlogService blogService;
    private List<Blog> testBlogs;

    @BeforeEach
    private void init() {

        blogDao = Mockito.mock(BlogDao.class);
        blogService = new BaseBlogService(blogDao);
        testBlogs = initBlogs();

    }

    @Test
    public void testGetAll() {

        Mockito.when(blogDao.getAll()).thenReturn(testBlogs);

        List<Blog> blogs = blogService.getAll();

        Assertions.assertEquals(testBlogs, blogs);
        Assertions.assertSame(testBlogs.size(), blogs.size());
        Assertions.assertFalse(blogs.isEmpty());

    }

    @Test
    public void testGetBlogById_Successful() {

        Blog testBlog = testBlogs.get(0);

        Mockito.when(blogDao.getBlogById(1)).thenReturn(testBlog);

        Blog blog = blogService.getBlogById(1);

        Assertions.assertNotNull(blog);
        Assertions.assertEquals(testBlog, blog);

    }

    @Test
    public void testGetBlogById_ReturnNull() {

        Mockito.when(blogDao.getBlogById(1)).thenReturn(null);

        Blog blog = blogService.getBlogById(1);

        Assertions.assertNull(blog);

    }

    @Test
    public void testCreateBlog_Successful() {

        BlogInput blogInput = new BlogInput(3, "name", 4);

        Mockito.when(blogDao.getBlogById(3)).thenReturn(null);

        blogService.createBlog(blogInput);

        Mockito.verify(blogDao, Mockito.times(1)).getBlogById(3);
        Mockito.verify(blogDao, Mockito.times(1)).createBlog(blogInput);

    }
    @Test
    public void testCreateBlog_ThrowException() {

        BlogInput blogInput = new BlogInput(3, "name", 1);

        Mockito.when(blogDao.getBlogById(3)).thenReturn(testBlogs.get(0));

        RuntimeException nullBlogInputException = Assertions.assertThrows(RuntimeException.class,
                () -> blogService.createBlog(null));
        RuntimeException blogExistsException = Assertions.assertThrows(RuntimeException.class,
                () -> blogService.createBlog(blogInput));

        Assertions.assertEquals("Blog is null", nullBlogInputException.getMessage());
        Assertions.assertEquals("Blog already present in DB", blogExistsException.getMessage());
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
