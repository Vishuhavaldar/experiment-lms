package com.library.demo.Repository;

import com.library.demo.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserModel, Integer> {

    @Query("select u from UserModel u where u.user_email = ?1")
    public UserModel getUserByEmail(String email);

    // Method to find a user by reset token
    public UserModel findByResetToken(String resetToken);

    // Optional: If you need case-insensitive reset token lookup
    @Query("select u from UserModel u where lower(u.resetToken) = lower(?1)")
    public UserModel findUserByResetTokenIgnoreCase(String resetToken);
}
