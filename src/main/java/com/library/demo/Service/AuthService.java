package com.library.demo.Service;

import com.library.demo.Model.UserModel;
import com.library.demo.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JavaMailSender mailSender;

    // Register a new user
    public UserModel register(UserModel user) {
        UserModel existingUser = userRepo.getUserByEmail(user.getUser_email());
        if (existingUser != null) {
            return null; // User already exists
        }
        return userRepo.save(user);
    }

    // User login
    public UserModel login(UserModel user) {
        UserModel existingUser = userRepo.getUserByEmail(user.getUser_email());

        if (existingUser != null && existingUser.getUser_password().equals(user.getUser_password())) {
            existingUser.setUser_password(""); // Clear password for security before returning user object
            return existingUser;
        }

        return null;
    }

    // Get username by ID
    public String getUsernameById(int id) {
        UserModel user = userRepo.findById(id).orElse(null);
        return user != null ? user.getUser_name() : null;
    }

    // Send password reset link via email
    public String sendResetLink(String email) {
    	email = email.trim(); 
        UserModel user = userRepo.getUserByEmail(email);
        if (user == null) {
            return "User not found!";
        }

        // Generate a unique reset token
        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(30)); // Token valid for 30 minutes

        // Send reset link via email
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Password Reset Request");
        String resetUrl = "http://localhost:4200/reset-password?token=" + token;
        mailMessage.setText("Click the following link to reset your password: " + resetUrl);
        mailSender.send(mailMessage);

        // Save the reset token and expiry in the database
        userRepo.save(user);

        return "Reset link sent!";
    }

    // Verify reset token
    public UserModel verifyResetToken(String token) {
        UserModel user = userRepo.findByResetToken(token); // Updated method call
        if (user == null || user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            return null; // Token is invalid or expired
        }
        return user;
    }

    // Reset the user's password
    public String resetPassword(String token, String newPassword) {
        UserModel user = verifyResetToken(token);
        if (user == null) {
            return "Invalid or expired reset token!";
        }

        user.setUser_password(newPassword); // Set the new password
        user.setResetToken(null); // Clear the token after successful password reset
        user.setResetTokenExpiry(null); // Clear the expiry
        userRepo.save(user);

        return "Password reset successfully!";
    }
}
