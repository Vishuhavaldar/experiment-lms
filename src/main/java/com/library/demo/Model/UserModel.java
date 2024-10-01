package com.library.demo.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class UserModel {

    @Id
    @GeneratedValue
    @Column
    private int id;

    @Column
    private String user_name;

    @Column
    private String user_email;

    @Column
    private String user_mobile;

    @Column
    private String user_password;

    @Column
    private String role;

    // Token for password reset via link
    @Column
    private String resetToken;

    // Expiry date for the reset token
    @Column
    private LocalDateTime resetTokenExpiry;

    // OTP for password reset via OTP
    @Column
    private String otp;

    // Expiry time for the OTP
    @Column
    private LocalDateTime otpExpiry;

    // Constructors, getters, and setters

    public UserModel() {}

    public UserModel(int id, String user_name, String user_email, String user_mobile, String user_password, String role) {
        this.id = id;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_mobile = user_mobile;
        this.user_password = user_password;
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", user_name='" + user_name + '\'' +
                ", user_email='" + user_email + '\'' +
                ", user_mobile='" + user_mobile + '\'' +
                ", user_password='" + user_password + '\'' +
                ", role='" + role + '\'' +
                ", resetToken='" + resetToken + '\'' +
                ", resetTokenExpiry=" + resetTokenExpiry +
                ", otp='" + otp + '\'' +
                ", otpExpiry=" + otpExpiry +
                '}';
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public LocalDateTime getResetTokenExpiry() {
        return resetTokenExpiry;
    }

    public void setResetTokenExpiry(LocalDateTime resetTokenExpiry) {
        this.resetTokenExpiry = resetTokenExpiry;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public LocalDateTime getOtpExpiry() {
        return otpExpiry;
    }

    public void setOtpExpiry(LocalDateTime otpExpiry) {
        this.otpExpiry = otpExpiry;
    }
}
