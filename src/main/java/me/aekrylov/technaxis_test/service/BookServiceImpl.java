package me.aekrylov.technaxis_test.service;

import me.aekrylov.technaxis_test.model.Book;
import me.aekrylov.technaxis_test.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 3/23/19 6:28 PM
 */
@Component
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
    public void update(Book book) {
        //todo
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean markRead(long bookId) {
        return repository.markRead(bookId);
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
    public Optional<Book> getById(long bookId) {
        return repository.findById(bookId);
    }
}
