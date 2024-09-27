package com.library.demo.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.library.demo.Repository.BookRepo;
import com.library.demo.Model.BookModel;




@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepo bookRepo;

    @Override
    public List<BookModel> getAllBooks() {
        return bookRepo.findAll();
    }

    @Override
    public void addBook(BookModel book) {
        bookRepo.save(book);
    }

    @Override
    public Boolean deleteBook(Integer id) {
        if (!bookRepo.existsById(id)) {
            return false;
        }
        bookRepo.deleteById(id);
        return true;
    }
    
  
    
    @Override
    public BookModel getBookById(Integer id) {
        return bookRepo.findById(id).orElse(null);
    }

//    @Override
//    public void updateBookById(Integer id, BookModel book) {
//        // Fetch the existing book
//        BookModel existingBook = bookRepo.findById(id).orElseThrow(() -> new RuntimeException("Book not found with id " + id));
//        // Update the existing book's properties
//        existingBook.setBook_name(book.getBook_name());
//        existingBook.setBook_author(book.getBook_author());
//        existingBook.setBook_description(book.getBook_description());
//        existingBook.setBook_image_url(book.getBook_image_url());
//        // Save the updated book
//        bookRepo.save(existingBook);
//    }
    
//    @Override
//    public BookModel updateBookById(Integer id, BookModel book) {
//        // Fetch the existing book
//        BookModel existingBook = bookRepo.findById(id).orElseThrow(() -> new RuntimeException("Book not found with id " + id));
//
//        // Update the existing book's properties
//        existingBook.setBook_name(book.getBook_name());
//        existingBook.setBook_author(book.getBook_author());
//        existingBook.setBook_description(book.getBook_description());
//        existingBook.setBook_image_url(book.getBook_image_url());
//
//        // Save and return the updated book
//        return bookRepo.save(existingBook);
//    }


    @Override
    public BookModel updateBookById(Integer id, BookModel book) {
        BookModel existingBook = bookRepo.findById(id).orElseThrow(() -> new RuntimeException("Book not found with id " + id));
        
        // Update book properties
        existingBook.setBook_name(book.getBook_name());
        existingBook.setBook_author(book.getBook_author());
        existingBook.setBook_description(book.getBook_description());
        existingBook.setBook_image_url(book.getBook_image_url());
        
        // Save and return the updated book
        return bookRepo.save(existingBook);
    }
}
