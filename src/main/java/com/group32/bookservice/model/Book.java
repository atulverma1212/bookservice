package com.group32.bookservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bookbase")
@Data
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer bookId;
    private String bookTitle;
    private String bookDescription;
    private String author;

    @Enumerated(EnumType.STRING)
    private BookState bookState;

    @ManyToOne
    @JoinColumn(name = "owner", referencedColumnName = "userId", nullable = false)
    private User owner;

    @ManyToOne
    @JoinColumn(name = "lended_user", referencedColumnName = "userId", nullable = false)
    private User currentUser;

    public Book(Integer bookId, String bookTitle, String bookDescription, User user, User currentUser) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookDescription = bookDescription;
        this.owner = user;
        this.currentUser = currentUser;
    }

}
