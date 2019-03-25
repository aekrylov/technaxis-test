package me.aekrylov.technaxis_test;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Objects;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 3/23/19 2:50 PM
 */
@Entity
@ApiModel(description = "Book model")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Unique identifier of a book", readOnly = true)
    private int id;

    @Column(nullable = false, length = 128)
    @ApiModelProperty(value = "Title of the book", required = true)
    private String title;

    @Lob
    @Column(nullable = false)
    @ApiModelProperty(value = "Description of a book", required = true)
    private String description;

    @Column(nullable = false, length = 128, updatable = false)
    @ApiModelProperty(value = "Author of a book", example = "Carl Jung", required = true)
    private String author;

    @Column(nullable = false, length = 20, unique = true)
    @ApiModelProperty(
            value = "ISBN of the current edition, in human-readable format",
            example = "978-0156612067",
            required = true
    )
    private String isbn;

    @Column(nullable = false)
    @ApiModelProperty(value = "Year of publishing of the current edition", example = "2001", required = true)
    private int printYear;

    @Column(nullable = false)
    @ApiModelProperty("Flag indicating if the book was read by someone")
    private boolean readAlready = false;

    @Column(nullable = false)
    @ApiModelProperty(
            value = "URL of the book cover",
            required = true,
            example = "https://technaxis-test.storage.yandexcloud.net/424f0f03-12b2-4a62-85c5-74e0aa8a3e21_jung%20modern%20man.jpg"
    )
    private String imageUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPrintYear() {
        return printYear;
    }

    public void setPrintYear(int printYear) {
        this.printYear = printYear;
    }

    public boolean isReadAlready() {
        return readAlready;
    }

    public void setReadAlready(boolean readAlready) {
        this.readAlready = readAlready;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return getId() == book.getId() &&
                getTitle().equals(book.getTitle()) &&
                getAuthor().equals(book.getAuthor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getAuthor());
    }
}
