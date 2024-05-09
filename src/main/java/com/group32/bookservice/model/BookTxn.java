package com.group32.bookservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "booktxn")
@Data
@NoArgsConstructor
public class BookTxn {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer txnId;

    @Enumerated(EnumType.STRING)
    private TxnState txnState;

    private String remarks;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "bookId", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "lender", referencedColumnName = "userId", nullable = false)
    private User lender;

    @ManyToOne
    @JoinColumn(name = "lendee", referencedColumnName = "userId", nullable = false)
    private User lendee;


    public BookTxn(Book book, User currentUser) {
        this.book = book;
        this.lendee = currentUser;
        this.lender = book.getOwner();
        this.txnState = TxnState.REQUESTED;
    }
}
