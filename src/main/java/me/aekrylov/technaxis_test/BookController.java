package me.aekrylov.technaxis_test;

import me.aekrylov.technaxis_test.model.Book;
import me.aekrylov.technaxis_test.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 3/23/19 6:38 PM
 */
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(path = "/")
    public ResponseEntity<PagedResources<Book>> list(Pageable pageable, PagedResourcesAssembler assembler) {
        return ResponseEntity.ok(assembler.toResource(
                bookService.get(pageable),
                linkTo(BookController.class).slash("/books").withSelfRel()
        ));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Book> getById(@PathVariable("id") long bookId) {
        return ResponseEntity.of(bookService.getById(bookId));
    }
}
