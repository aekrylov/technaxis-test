package me.aekrylov.technaxis_test;

import me.aekrylov.technaxis_test.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 3/23/19 6:28 PM
 */
public interface BookService {

    Book create(Book book);

    void update(long id, String title, String description, String isbn, Integer printYear);

    void delete(long id);

    boolean markRead(long bookId);

    Page<Book> get(Pageable pageable);

    Page<Book> get(String query, Pageable pageable);

    Optional<Book> getById(long bookId);
}
