package com.library.demo.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BookModel {
    @Id
    @GeneratedValue
    @Column
    private int id;

    @Column
    private String book_name;

    @Column
    private String book_author;

    @Column
    private String book_description; // Field for book description

    @Column
    private String book_image_url; // Field for image URL

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_author() {
        return book_author;
    }

    public void setBook_author(String book_author) {
        this.book_author = book_author;
    }

    public String getBook_description() {
        return book_description;
    }

    public void setBook_description(String book_description) {
        this.book_description = book_description;
    }

    public String getBook_image_url() {
        return book_image_url;
    }

    public void setBook_image_url(String book_image_url) {
        this.book_image_url = book_image_url;
    }
}
