package com.group32.bookservice.repository;


import com.group32.bookservice.model.Book;
import com.group32.bookservice.model.Role;
import com.group32.bookservice.model.RoleEnum;
import com.group32.bookservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    Optional<Book> findByOwner(User user);

    Optional<Book> findByBookTitle(String title);

    Optional<Book> findByBookTitleAndOwner(String title, User user);

    @Query(value = "select * from bookbase where book_title like CONCAT('%', ?1, '%')", nativeQuery = true)
    List<Book> findByBookTitleLike(String title);


}
