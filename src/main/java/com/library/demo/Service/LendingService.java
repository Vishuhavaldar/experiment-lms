package com.library.demo.Service;

import java.util.List;

import com.library.demo.Model.LendingModel;
import com.library.demo.Model.UserModel;

public interface LendingService {
    public LendingModel addBorrowBook(LendingModel ls);
    
    public List<String> getBooksBorrowed();
    
    public List<LendingModel> getBooksBorrowedByUser(Integer uid);  // Updated return type
    
    public List<UserModel> getUsersBorrowedBook(Integer bid);
    
    public Boolean returnBook(String bookname);
}
