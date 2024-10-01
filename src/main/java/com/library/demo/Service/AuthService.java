package com.library.demo.Service;

import com.library.demo.Model.UserModel;
import com.library.demo.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.Random;
import org.springframework.mail.MailException;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
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
//    public String sendResetLink(String email) {
//    	email = email.trim(); 
//        UserModel user = userRepo.getUserByEmail(email);
//        if (user == null) {
//            return "User not found!";
//        }
//
//        // Generate a unique reset token
//        String token = UUID.randomUUID().toString();
//        user.setResetToken(token);
//        user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(30)); // Token valid for 30 minutes
//
//        // Send reset link via email
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setTo(email);
//        mailMessage.setSubject("Password Reset Request");
//        String resetUrl = "http://localhost:4200/reset-password?token=" + token;
//        mailMessage.setText("Click the following link to reset your password: " + resetUrl);
//        mailSender.send(mailMessage);
//
//        // Save the reset token and expiry in the database
//        userRepo.save(user);
//
//        return "Reset link sent!";
//    }
    
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

        // Send reset link via email with both token and email
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Password Reset Request");

        // Include both token and email in the reset link
        String resetUrl = "http://localhost:4200/reset-password?token=" + token + "&email=" + email;
        mailMessage.setText("Click the following link to reset your password: " + resetUrl);
        mailSender.send(mailMessage);

        // Save the reset token and expiry in the database
        userRepo.save(user);

        return "Reset link sent!";
    }


    // Verify reset token
//    public UserModel verifyResetToken(String token) {
//        UserModel user = userRepo.findByResetToken(token); // Updated method call
//        if (user == null || user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
//            return null; // Token is invalid or expired
//        }
//        return user;
//    }
 // Verify reset token and email
    public UserModel verifyResetToken(String token, String email) {
        UserModel user = userRepo.findByResetTokenAndEmail(token, email); // Verify both token and email
        if (user == null || user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            return null; // Token is invalid or expired
        }
        return user;
    }


//    // Reset the user's password
//    public boolean resetPassword( String email,String token, String newPassword) {
//        UserModel user = verifyResetToken( email,token); // Find by both token and email
//
//        if (user == null || user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
//            return false; // Token is invalid or expired
//        }
//
//        // Set the new password and clear the token/expiry
//        user.setUser_password(newPassword);
//        user.setResetToken(null);
//        user.setResetTokenExpiry(null);
//        userRepo.save(user);
//
//        return true;  // Password reset was successful
//    }
    public boolean resetPassword(String email, String token, String newPassword) {
    	email = email.trim();
    	newPassword = newPassword.trim();
        UserModel user = verifyResetToken(token, email);
        if (user == null) {
            System.out.println("No user found for email: " + email + " and token: " + token);
            return false;  // No matching user or token
        }
        if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            System.out.println("Token expired for email: " + email);
            return false;  // Token expired
        }

        // Reset the password
        user.setUser_password(newPassword);
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepo.save(user);
        return true;
    }

    // Inject the PasswordEncoder



    
    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // 6-digit OTP
        return String.valueOf(otp);
    }

    // Send OTP to the user's email
    public String sendOtp(String email) {
        String otp = generateOtp();  // Assume this method generates your OTP
        email = email.trim(); 
        UserModel user = userRepo.getUserByEmail(email);
        if (user == null) {
            return "User not found!";
        }
        user.setOtp(otp);  // Set OTP to the user
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(5)); // Set expiry

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Your OTP");
        mailMessage.setText("Your OTP is: " + otp);
        try {
            mailSender.send(mailMessage);
            userRepo.save(user);
            return "OTP sent!";
        } catch (MailException e) {
            return "Failed to send OTP";
        }
    }


    // Verify OTP and reset the password
    public String verifyOtpAndResetPassword(String email, String otp, String newPassword) {
        UserModel user = userRepo.getUserByEmail(email);
        if (user == null || !otp.equals(user.getOtp()) || user.getOtpExpiry().isBefore(LocalDateTime.now())) {
            return "Invalid or expired OTP!";
        }

        // Reset password and clear OTP
        user.setUser_password(newPassword);
        user.setOtp(null);
        user.setOtpExpiry(null);
        userRepo.save(user);

        return "Password reset successfully!";
    }

}
