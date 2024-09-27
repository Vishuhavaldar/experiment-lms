package com.library.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.library.demo.Model.BookModel;
import com.library.demo.Service.BookServiceImpl;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
@RestController
public class BookController {

    @Autowired
    BookServiceImpl bookService;

    @GetMapping("/")
    public String hello() {
        return "hello";
    }

    @PostMapping("/addBook")
    public ResponseEntity<?> addBook(@RequestBody BookModel book) {
        bookService.addBook(book);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/allBooks")
    public ResponseEntity<List<BookModel>> getBooks() {
        List<BookModel> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/allBooks/{id}")
    public ResponseEntity<BookModel> getBookById(@PathVariable Integer id) {
        Optional<BookModel> book = Optional.ofNullable(bookService.getBookById(id));
        if (book.isPresent()) {
            return ResponseEntity.ok(book.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delBook/{bid}")
    public ResponseEntity<?> deleteBook(@PathVariable Integer bid) {
        boolean isDeleted = bookService.deleteBook(bid);
        if (isDeleted) {
            return ResponseEntity.ok("Book deleted");
        } else {
            return ResponseEntity.ok("Book deletion failed");
        }
    }

//    @PutMapping("/update")
//    public ResponseEntity<?> updateBook(@RequestBody BookModel book) {
//        bookService.updateBook(book);
//        return ResponseEntity.ok("Book updated successfully");
//    }
    
//    @PutMapping("/updateBook/{id}")
//    public ResponseEntity<?> updateBook(@PathVariable Integer id, @RequestBody BookModel book) {
//        if (!id.equals(book.getId())) {
//            return ResponseEntity.badRequest().body("Book ID in URL and payload do not match");
//        }
//        bookService.updateBookById(id, book); // Call the new method here
//        return ResponseEntity.ok("Book updated successfully");
//    }

//    @PutMapping("/updateBook/{id}")
//    public ResponseEntity<?> updateBook(@PathVariable Integer id, @RequestBody BookModel book) {
//        // Ensure the book's ID matches the path variable
//        if (!id.equals(book.getId())) {
//            return ResponseEntity.badRequest().body("Book ID in URL and payload do not match");
//        }
//
//        // Call the service method to update the book
//        bookService.updateBookById(id,book);
//
//        // Return a success response with the updated book or a success message
//        return ResponseEntity.ok("Book updated successfully");
//    }
    
    @PutMapping("/updateBook/{id}")
    public ResponseEntity<BookModel> updateBook(@PathVariable Integer id, @RequestBody BookModel book) {
        // Ensure the book's ID matches the path variable
        if (!id.equals(book.getId())) {
            return ResponseEntity.badRequest().body(null);
        }

        // Update the book and return the updated instance
        BookModel updatedBook = bookService.updateBookById(id, book);
        return ResponseEntity.ok(updatedBook);
    }


}
