package com.group32.bookservice.controller;

import com.group32.bookservice.dto.AddBookRequest;
import com.group32.bookservice.model.Book;
import com.group32.bookservice.model.User;
import com.group32.bookservice.services.BookService;
import com.group32.bookservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/books")
@RestController
@RequiredArgsConstructor
public class BookController {
    private final UserService userService;
    private final BookService bookService;

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Book> addBook(@RequestBody AddBookRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(bookService.addBook(request, currentUser));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<Book>> allBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @DeleteMapping("/remove/{title}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> delete(@PathVariable String title) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        bookService.deleteBook(currentUser, title);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/request/{bookId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> requestBook(@PathVariable Integer bookId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        bookService.requestBook(bookId, currentUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Book>> search(@RequestParam String search) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        List<Book> search1 = bookService.search(search);
        return ResponseEntity.ok(search1);
    }

}
