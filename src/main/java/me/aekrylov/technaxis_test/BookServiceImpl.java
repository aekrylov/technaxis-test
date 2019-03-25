package me.aekrylov.technaxis_test;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 3/23/19 6:28 PM
 */
@Component
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book create(Book book) {
        return repository.save(book);
    }

    @Override
    public void update(int id, String title, String description, String isbn, Integer printYear) {
        Book book = getById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        if (title != null) {
            book.setTitle(title);
        }
        if (description != null) {
            book.setDescription(description);
        }
        if (isbn != null) {
            book.setIsbn(isbn);
        }
        if (printYear != null) {
            book.setPrintYear(printYear);
        }
        book.setReadAlready(false);
        repository.save(book);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }

    @Override
    public void markRead(int bookId) {
        if (repository.markRead(bookId) == 0) {
            throw new BookNotFoundException(bookId);
        }
    }

    @Override
    public Page<Book> get(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Book> get(String query, Pageable pageable) {
        return repository.findAllByDescriptionContainsIgnoreCase(query, pageable);
    }

    @Override
    public Optional<Book> getById(int bookId) {
        return repository.findById(bookId);
    }
}
