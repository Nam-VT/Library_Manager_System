package com.example.librarysystem;

import java.util.Date;

public class ReturnBookData {
    private final String student_name;
    private final String book_name;
    private final Date issue_date;
    private final Date due_date;

    public ReturnBookData(String student_name, String book_name, Date issue_date, Date due_date) {
        this.student_name = student_name;
        this.book_name = book_name;
        this.issue_date = issue_date;
        this.due_date = due_date;
    }

    // Getters
    public String getStudent_name() { return student_name; }
    public String getBook_name() { return book_name; }
    public Date getIssue_date() { return issue_date; }
    public Date getDue_date() { return due_date; }
}
