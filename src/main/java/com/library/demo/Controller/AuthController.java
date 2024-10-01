package com.library.demo.Controller;

import com.library.demo.Model.UserModel;
import com.library.demo.Repository.UserRepo;
import com.library.demo.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


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
//    @PostMapping("/reset-password")
//    public String resetPassword(@RequestParam String token, @RequestParam String newPassword) {
//        return authService.resetPassword(token, newPassword);
//    }
//    @PostMapping("/reset-password")
//    public String resetPassword(@RequestParam String token, @RequestParam String email, @RequestParam String newPassword) {
//        return authService.resetPassword(token, email, newPassword);
//    }

    
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
//    @PostMapping("/reset-password")
//    public ResponseEntity<String> resetPassword(
//    		 @RequestParam String email,
//           @RequestParam String token, 
//         @RequestParam String newPassword) {
//    	System.out.println("Resetting password for Email: " + email + ", Token: " + token);
//        // Call the AuthService method to reset the password, which now returns a boolean
//        boolean success = authService.resetPassword( email,token, newPassword);
//
//        if (success) {
//            return ResponseEntity.ok("Password reset successful");
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token or expired");
//        }
//    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
        @RequestParam String email,
        @RequestParam String token, 
        @RequestParam String newPassword) {

        // Log the received parameters for debugging
        System.out.println("Received Email: " + email);
        System.out.println("Received Token: " + token);
        System.out.println("Received New Password: " + newPassword);

        boolean success = authService.resetPassword(email, token, newPassword);

        if (success) {
            return ResponseEntity.ok("Password reset successful");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token or expired");
        }
    }

    // Send OTP for password reset
    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestParam String email) {
        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Email parameter is missing");
        }
        // Your OTP sending logic here
        return ResponseEntity.ok("OTP sent to " + email);
    }


    // Verify OTP and reset password
    @PostMapping("/verify-otp-reset-password")
    public ResponseEntity<String> verifyOtpAndResetPassword(
            @RequestParam String email,
            @RequestParam String otp,
            @RequestParam String newPassword) {
        String result = authService.verifyOtpAndResetPassword(email, otp, newPassword);
        if (result.equals("Password reset successfully!")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }


}
