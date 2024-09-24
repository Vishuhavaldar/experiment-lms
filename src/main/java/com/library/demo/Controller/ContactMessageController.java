package com.library.demo.Controller;
import java.util.List;

import com.library.demo.Model.ContactMessage;
import com.library.demo.Service.ContactMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/contact")
public class ContactMessageController {

    @Autowired
    private ContactMessageService contactMessageService;

    @PostMapping
    public ResponseEntity<ContactMessage> createContactMessage(@RequestBody ContactMessage contactMessage) {
        ContactMessage savedMessage = contactMessageService.saveMessage(contactMessage);
        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping
    public ResponseEntity<List<ContactMessage>> getAllMessages() {
        List<ContactMessage> messages = contactMessageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }
}
