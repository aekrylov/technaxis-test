package me.aekrylov.technaxis_test.repository;

import me.aekrylov.technaxis_test.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 3/23/19 2:50 PM
 */
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {

    @Query("update Book set readAlready = true where id = :bookId")
    @Modifying
    boolean markRead(long bookId);

    //todo FTS
    Page<Book> findAllByDescriptionContainsIgnoreCase(String query, Pageable pageable);
    
}
