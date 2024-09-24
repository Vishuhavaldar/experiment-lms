package com.library.demo.Repository;

import com.library.demo.Model.BookModel;
import com.library.demo.Model.LendingModel;
import com.library.demo.Model.UserModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LendingRepo extends JpaRepository<LendingModel, Integer> {

    // All books taken
    @Query("select book_name from BookModel where id in (select book_id from LendingModel)")
    public List<String> getBooksBorrowed();

    // All books taken by a specific user
    @Query("select l from LendingModel l where l.user_id = ?1")
    public List<LendingModel> findLendingsByUserId(Integer uid);  // Updated to return LendingModel
    
    // All students who have taken a particular book
    @Query("select u from UserModel u where id in (select user_id from LendingModel where book_id = ?1)")
    public List<UserModel> getUsersBorrowedBook(Integer bid);

    @Transactional
    @Modifying
    @Query("delete from LendingModel l where l.book_id in (select id from BookModel where book_name = ?1)")
    public Integer returnBook(String bookname);
}
