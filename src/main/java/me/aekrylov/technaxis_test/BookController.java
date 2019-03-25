package me.aekrylov.technaxis_test;

import io.swagger.annotations.*;
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
@RequestMapping(value = "/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @ApiOperation("List books with pagination")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "Zero indexed page number",
                    paramType = "query", dataTypeClass = Integer.class, defaultValue = "0"),
            @ApiImplicitParam(name = "size", value = "Page size",
                    paramType = "query", dataTypeClass = Integer.class, defaultValue = "20"),
            @ApiImplicitParam(name = "sort", value = "Sorting specification",
                    paramType = "query", dataTypeClass = String.class, example = "title,desc", allowMultiple = true)
    })
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Book>> list(Pageable pageable,
                                           @ApiParam("Optional full-text search query")
                                           @RequestParam(name = "query", required = false) String query) {
        Page<Book> books = query != null
                ? bookService.get(query, pageable)
                : bookService.get(pageable);

        return ResponseEntity.ok(books);
    }

    @ApiOperation("Create a new book")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Book created, id of the new book is returned")
    })
    @PostMapping(path = "")
    public ResponseEntity create(@RequestBody Book book) {
        book = bookService.create(book);
        return ResponseEntity.status(201).body(book.getId());
    }

    @ApiOperation("Find book by id")
    @GetMapping(path = "/{id}")
    public ResponseEntity<Book> getById(@PathVariable("id") int bookId) {
        return ResponseEntity.of(bookService.getById(bookId));
    }

    @ApiOperation(
            value = "Update existing book",
            notes = "Only title, description, isbn and print year can be changed; markRead resets to false"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful update"),
            @ApiResponse(code = 404, message = "Book with specified ID not found")
    })
    @RequestMapping(path = "/{id}", method = {POST, PUT, PATCH})
    public ResponseEntity update(@PathVariable("id") int id,
                                 @RequestParam(required = false) String title,
                                 @RequestParam(required = false) String description,
                                 @RequestParam(required = false) String isbn,
                                 @RequestParam(required = false) Integer printYear) {
        bookService.update(id, title, description, isbn, printYear);
        return ResponseEntity.ok("ok");
    }

    @ApiOperation("Delete a book")
    @RequestMapping(path = "/{id}", method = DELETE)
    public ResponseEntity delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return ResponseEntity.ok("ok");
    }

    @ApiOperation("Mark book as read")
    @RequestMapping(path = "/{id}/mark-read", method = POST)
    public ResponseEntity markRead(@PathVariable("id") int id) {
        bookService.markRead(id);
        return ResponseEntity.ok("ok");
    }
}
