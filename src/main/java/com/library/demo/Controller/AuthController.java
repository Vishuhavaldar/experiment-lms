package com.library.demo.Controller;

import com.library.demo.Model.UserModel;
import com.library.demo.Repository.UserRepo;
import com.library.demo.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    
    @Autowired
    private UserRepo userRepo;

    // User login
    @PostMapping("/login")
    public UserModel login(@RequestBody UserModel user) {
        return authService.login(user);
    }

    // User registration
    @PostMapping("/register")
    public UserModel register(@RequestBody UserModel user) {
        System.out.println("register");
        return authService.register(user);
    }

    // Get username by ID
    @GetMapping("/username/{id}")
    public String username(@PathVariable int id) {
        return authService.getUsernameById(id);
    }

    // Send password reset link
    @PostMapping("/send-reset-link")
    public String sendResetLink(@RequestParam String email) {
        return authService.sendResetLink(email);
    }

    // Reset password
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        return authService.resetPassword(token, newPassword);
    }
    
 // Test endpoint to check if UserRepo is working correctly
    @GetMapping("/test-user-by-email")
    public UserModel testGetUserByEmail(@RequestParam String email) {
    	  email = email.trim();
        System.out.println("Looking for user with email: " + email);
        UserModel user = userRepo.getUserByEmail(email);

        if (user == null) {
            System.out.println("User with email " + email + " not found.");
        } else {
            System.out.println("User found: " + user.toString());
        }

        return user;  // Return user if found, or null if not found
    }
}
