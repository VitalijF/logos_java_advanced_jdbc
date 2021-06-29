package service.imp;

import dao.BlogDao;
import dao.UserDao;
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

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BaseBlogServiceTest {
  private BlogDao blogDao;

  private BaseBlogService baseBlogService;

  private static List<Blog> BLOGS = initBlogs();


  @BeforeEach
  public void init() {
    blogDao = Mockito.mock(BlogDao.class);
    baseBlogService = new BaseBlogService(blogDao);
  }

  @Test
  void testAllGetBlogs() {
    List<Blog> expectedBlogsFromDB = initBlogs();
    Mockito.when(blogDao.getAllBlogs()).thenReturn(expectedBlogsFromDB);

    List<Blog> allBlogs = baseBlogService.getAllBlogs();

    Assertions.assertNotNull(allBlogs);
    Assertions.assertSame(2, allBlogs.size());
    Assertions.assertEquals(expectedBlogsFromDB, allBlogs);
  }

  @Test
  void testGetBlogByIdSuccessful(){
    Blog blog = BLOGS.get(1);
    Mockito.when(blogDao.getBlogById(2)).thenReturn(blog);

    Blog thisBlog = baseBlogService.getBlogById(2);

    Assertions.assertNotNull(thisBlog);
    Assertions.assertEquals(2, thisBlog.getId());
  }

  @Test
  void testGetBlogByIdFailed(){
    Mockito.when(blogDao.getBlogById(2)).thenReturn(null);

    Blog thisBlog = baseBlogService.getBlogById(2);

    Assertions.assertNull(thisBlog);
  }

  @Test
  void testCreateBlogExceptionThrow() {
    Blog blog = BLOGS.get(1);
    BlogInput blogInput = new BlogInput(blog.getId(), blog.getName(), blog.getUser().getId());
    Mockito.when(blogDao.getBlogById(2)).thenReturn(blog);
    RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> baseBlogService.createBlog(blogInput)
    );


    Mockito.verify(blogDao, Mockito.times(1))
            .getBlogById(2);
    Mockito.verify(blogDao, Mockito.never())
            .createBlog(blogInput);
    Assertions.assertEquals("Already existed blog with such id in DB", exception.getMessage());
  }

  @Test
  void testCreateBlogSuccessfully() {
    Blog blog = BLOGS.get(1);
    BlogInput blogInput = new BlogInput(blog.getId(), blog.getName(), blog.getUser().getId());

    Mockito.when(blogDao.getBlogById(2)).thenReturn(null);

    baseBlogService.createBlog(blogInput);

    Mockito.verify(blogDao, Mockito.times(1))
            .getBlogById(2);
    Mockito.verify(blogDao, Mockito.times(1))
            .createBlog(blogInput);
  }

  private static List<Blog> initBlogs() {
    return List.of(
            new Blog(1,
                    "Windows 10",
                    new User(1, "Stepan", "Velykiy", LocalDate.of(1996,7,2))),
            new Blog(2,
                    "firstName2",
                    new User(2, "Vasyl", "Malyi", LocalDate.of(2000,6,18)))
    );
  }
}
