package me.aekrylov.technaxis_test.service;

import me.aekrylov.technaxis_test.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 3/23/19 6:28 PM
 */
public interface BookService {

    void create(Book book);

    void update(Book book);

    boolean markRead(long bookId);

    Page<Book> get(Pageable pageable);

    Optional<Book> getById(long bookId);
}
