package com.library.demo.Service;

import java.util.List;

import com.library.demo.Model.BookModel;
import com.library.demo.Model.LendingModel;
import com.library.demo.Model.UserModel;
import com.library.demo.Repository.BookRepo;
import com.library.demo.Repository.LendingRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LendingServiceImpl implements LendingService {

    @Autowired
    private LendingRepo lendingRepo;
    
    @Autowired
    private BookRepo bookRepo;

    @Override
    public LendingModel addBorrowBook(LendingModel ls) {
        return lendingRepo.save(ls);
    }

    @Override
    public List<String> getBooksBorrowed() {
        return lendingRepo.getBooksBorrowed();
    }

    // Updated to return List<LendingModel> and include book details
    @Override
    public List<LendingModel> getBooksBorrowedByUser(Integer uid) {
        List<LendingModel> lendings = lendingRepo.findLendingsByUserId(uid);
        
        // Populate LendingModel with BookModel details
        lendings.forEach(lending -> {
            BookModel book = bookRepo.findById(lending.getBook_id()).orElse(null);
            if (book != null) {
                lending.setBook_name(book.getBook_name());
                lending.setBook_author(book.getBook_author());
                lending.setBook_description(book.getBook_description());
                lending.setBook_image_url(book.getBook_image_url());
            }
        });
        return lendings;
    }

    @Override
    public List<UserModel> getUsersBorrowedBook(Integer bid) {
        return lendingRepo.getUsersBorrowedBook(bid);
    }

    @Override
    public Boolean returnBook(String bookname) {
        lendingRepo.returnBook(bookname);
        return true;
    }
}
