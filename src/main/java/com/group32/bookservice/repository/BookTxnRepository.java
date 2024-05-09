package com.group32.bookservice.repository;

import com.group32.bookservice.model.Book;
import com.group32.bookservice.model.BookTxn;
import com.group32.bookservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookTxnRepository extends JpaRepository<BookTxn, Integer> {

    Optional<BookTxn> findByBookAndLendee(Book book, User user);

}
