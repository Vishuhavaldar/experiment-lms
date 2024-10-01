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
//    @Query("select u from UserModel u where lower(u.resetToken) = lower(?1)")
//    public UserModel findUserByResetTokenIgnoreCase(String resetToken);
    @Query("SELECT u FROM UserModel u WHERE u.resetToken = ?1 AND u.user_email = ?2")
    UserModel findByResetTokenAndEmail(String resetToken, String email);

}
