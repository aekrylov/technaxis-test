package me.aekrylov.technaxis_test;

import me.aekrylov.technaxis_test.storage.StorageService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 3/23/19 8:47 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
public class BookControllerTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @MockBean
    private StorageService storageService; //mocked because we set up the full context but don't have storage keys

    private Book book;

    @Before
    public void setUp() {
        book = bookService.create(newBook("Book1", true));
    }

    @After
    public void tearDown() {
        bookRepository.deleteAll();
    }

    @Test
    public void listBooks() throws Exception {
        for (int i = 2; i <= 5; i++) {
            bookService.create(newBook("Book" + i, i % 4 == 0));
        }

        this.mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andDo(document("books-get"));
    }

    @Test
    public void listBooks_pageSize() throws Exception {
        for (int i = 2; i <= 10; i++) {
            bookService.create(newBook("Book" + i, i % 4 == 0));
        }

        this.mvc.perform(get("/books")
                .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)))
                .andExpect(jsonPath("$.content[0].title", is("Book1")))
                .andExpect(jsonPath("$.totalPages", is(2)))
                .andDo(document("books-get-size"));
    }

    @Test
    public void listBooks_sortDesc() throws Exception {
        for (int i = 2; i <= 5; i++) {
            bookService.create(newBook("Book" + i, i % 4 == 0));
        }

        this.mvc.perform(get("/books")
                .param("sort", "title,desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title", is("Book5")))
                .andExpect(jsonPath("$.content[4].title", is("Book1")))
                .andDo(document("books-get-sort-desc"));
    }

    @Test
    public void updateTest() throws Exception {
        String newTitle = "Book2";

        this.mvc.perform(post("/books/" + book.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .param("title", newTitle))
                .andExpect((result) -> {
                    Book newVersion = bookService.getById(book.getId()).get();
                    assertEquals(newVersion.getTitle(), newTitle);
                    assertFalse(newVersion.isReadAlready());
                })
                .andDo(document("book-post"));
    }

    @Test
    public void testUpdate404() throws Exception {
        this.mvc.perform(post("/books/1337")
                .param("title", "New title"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Book with id 1337 not found"))
                .andDo(document("books-404"));
    }

    private Book newBook(String str, boolean read) {
        Book book = new Book();
        book.setAuthor(str);
        book.setDescription(str);
        book.setImageUrl(str);
        book.setIsbn(str);
        book.setPrintYear(2018);
        book.setTitle(str);
        book.setReadAlready(read);

        return book;
    }
}
