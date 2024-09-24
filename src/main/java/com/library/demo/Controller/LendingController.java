package com.library.demo.Controller;

import com.library.demo.Model.LendingModel;
import com.library.demo.Model.UserModel;
import com.library.demo.Service.LendingServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class LendingController {

    @Autowired
    private LendingServiceImpl lendingService;

    @PostMapping("/borrowBook")
    public LendingModel borrowBook(@RequestBody LendingModel lend) {
        return lendingService.addBorrowBook(lend);
    }

    @GetMapping("/getBooksBorrowed")
    public List<String> getBooksBorrowed() {
        return lendingService.getBooksBorrowed();
    }

    @GetMapping("/getBooksBorrowedByUser/{uid}")
    public List<LendingModel> getBooksBorrowedByUser(@PathVariable Integer uid) {  // Updated return type
        return lendingService.getBooksBorrowedByUser(uid);
    }

    @GetMapping("/getUsersBorrowedBook/{bid}")
    public List<UserModel> getUsersBorrowedBook(@PathVariable Integer bid) {
        return lendingService.getUsersBorrowedBook(bid);
    }

    @DeleteMapping("/returnBook/{bookname}")
    public ResponseEntity<String> returnBook(@PathVariable String bookname) {
        lendingService.returnBook(bookname);
        return ResponseEntity.ok("{\"message\": \"Book Returned\"}");
    }
}
