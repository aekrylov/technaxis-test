package me.aekrylov.technaxis_test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * TODO HATEOAS
 * TODO response codes
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 3/23/19 6:38 PM
 */
@RestController
@RequestMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(path = "")
    public ResponseEntity<Page<Book>> list(Pageable pageable,
                                           @RequestParam(name = "query", required = false) String query) {
        Page<Book> books = query != null
                ? bookService.get(query, pageable)
                : bookService.get(pageable);

        return ResponseEntity.ok(books);
    }

    @PostMapping(path = "")
    public ResponseEntity create(@RequestParam Book book) {
        book = bookService.create(book);
        return ResponseEntity.ok(book.getId());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Book> getById(@PathVariable("id") long bookId) {
        return ResponseEntity.of(bookService.getById(bookId));
    }

    @RequestMapping(path = "/{id}", method = {POST, PUT, PATCH})
    public ResponseEntity update(@PathVariable("id") long id,
                                 @RequestParam(required = false) String title,
                                 @RequestParam(required = false) String description,
                                 @RequestParam(required = false) String isbn,
                                 @RequestParam(required = false) Integer printYear) {
        bookService.update(id, title, description, isbn, printYear);
        return ResponseEntity.ok("ok");
    }

    @RequestMapping(path = "/{id}", method = DELETE)
    public ResponseEntity delete(@PathVariable("id") long id) {
        bookService.delete(id);
        return ResponseEntity.ok("ok");
    }

    @RequestMapping(path = "/{id}/mark-read", method = POST)
    public ResponseEntity markRead(@PathVariable("id") long id) {
        bookService.markRead(id);
        //todo check if was updated
        return ResponseEntity.ok("ok");
    }
}
