package com.group32.bookservice.services;

import com.group32.bookservice.dto.AddBookRequest;
import com.group32.bookservice.exceptions.EntityAlreadyExistsException;
import com.group32.bookservice.exceptions.InvalidRequestException;
import com.group32.bookservice.model.Book;
import com.group32.bookservice.model.BookState;
import com.group32.bookservice.model.BookTxn;
import com.group32.bookservice.model.User;
import com.group32.bookservice.repository.BookRepository;
import com.group32.bookservice.repository.BookTxnRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookTxnRepository bookTxnRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book addBook(AddBookRequest addBookRequest, User user) {

        Optional<Book> byBookTitle = bookRepository.findByBookTitle(addBookRequest.getTitle());

        if(byBookTitle.isPresent()) {
            throw new EntityAlreadyExistsException("Book with title " + addBookRequest.getTitle() + " already exists");
        }

        Book book = new Book();
        book.setBookTitle(addBookRequest.getTitle());
        book.setAuthor(addBookRequest.getAuthor());
        book.setBookDescription(addBookRequest.getDescription());
        book.setBookState(BookState.AVAILABLE);
        book.setCurrentUser(user);
        book.setOwner(user);
        return bookRepository.save(book);
    }

    public void deleteBook(User currentUser, String title) {
        Optional<Book> byBookTitle = bookRepository.findByBookTitleAndOwner(title, currentUser);

        if(byBookTitle.isEmpty()) {
            throw new EntityNotFoundException("Book with title " + title + " does not exist");
        }

        bookRepository.delete(byBookTitle.get());
    }

    public void requestBook(Integer bookId, User currentUser) {

        Optional<Book> book = bookRepository.findById(bookId);

        if(book.isEmpty()) {
            throw new EntityNotFoundException("Book with bookId " + bookId + " does not exist");
        }

        if(book.get().getOwner().equals(currentUser)) {
            throw new InvalidRequestException("Why are you trying to request a book owned by you?");
        }

        Optional<BookTxn> byBookAndLendee = bookTxnRepository.findByBookAndLendee(book.get(), currentUser);
        if(byBookAndLendee.isPresent()) {
            throw new EntityAlreadyExistsException("Book Request already exists. TxnId: " + byBookAndLendee.get().getTxnId());
        }

        if(! book.get().getBookState().equals(BookState.AVAILABLE)) {
            throw new InvalidRequestException("Book with bookId " + bookId + " is not available for Borrowing");
        }

        BookTxn bookTxn = new BookTxn(book.get(), currentUser);
        bookTxnRepository.save(bookTxn);

        book.get().setBookState(BookState.REQUESTED);
        bookRepository.save(book.get());
    }

    public List<Book> search(String search) {
        return bookRepository.findByBookTitleLike(search);
    }
}
