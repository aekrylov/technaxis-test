package me.aekrylov.technaxis_test;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 3/23/19 9:49 PM
 */
public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(int id) {
        super(String.format("Book with id %d not found", id));
    }
}
