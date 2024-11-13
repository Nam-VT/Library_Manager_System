package com.example.librarysystem;

import java.util.Date;

public class AvailableBookData {
    private final String title;
    private final String type;
    private final String author;
    private final int quantity;
    private final Date pulished_date;
    private final int book_id;

    public AvailableBookData(String title, String type, String author, int quantity, java.sql.Date pulished_date, int book_id) {
        this.title = title;
        this.type = type;
        this.author = author;
        this.quantity = quantity;
        this.pulished_date = pulished_date;
        this.book_id = book_id;
    }

    public String getType() { return  type; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getQuantity() { return quantity; }
    public Date getDate() { return pulished_date; }
    public int getBook_id() { return book_id; }
}
