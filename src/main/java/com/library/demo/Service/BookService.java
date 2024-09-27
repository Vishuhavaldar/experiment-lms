package com.library.demo.Service;

import com.library.demo.Model.BookModel;
import java.util.List;

public interface BookService {
    List<BookModel> getAllBooks();
    void addBook(BookModel book);
    Boolean deleteBook(Integer id);
    BookModel getBookById(Integer id);
    BookModel updateBookById(Integer id, BookModel book); // Return type should be consistent
}
