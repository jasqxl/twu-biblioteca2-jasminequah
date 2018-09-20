package com.twu.biblioteca;

public class Book {

    private String title;
    private String author;
    private int publishYear;
    private boolean isAvailStatus = true;
    private String bookDetails;

    public Book() {}

    public Book(String title, String author, int publishYear, boolean isAvailStatus) {
        this.title = title;
        this.author = author;
        this.publishYear = publishYear;
        this.isAvailStatus = isAvailStatus;
        formBookDetails();
    }

    private void formBookDetails() {
        String bookStatus;
        if (this.isAvailStatus) bookStatus = "Available";
        else bookStatus = "Unavailable";

        this.bookDetails = String.format("%-30s", title) + "|" + String.format("%-20s", author) + "|" + String.format("%-8s", Integer.toString(publishYear)) + "|" + bookStatus;
    }

    public Book(String bookDetails) {
        this.title = bookDetails.substring(0, 30).trim();
        this.author = bookDetails.substring(31, 51).trim();
        this.publishYear = Integer.parseInt(bookDetails.substring(52, 56));
        if (bookDetails.substring(61, bookDetails.length()).toLowerCase().indexOf("unavailable") != -1) {
            this.isAvailStatus = false;
        }
        else if(bookDetails.substring(61, bookDetails.length()).toLowerCase().indexOf("available") != -1)  {
            this.isAvailStatus = true;
        }
        this.bookDetails = bookDetails;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public int getPublishYear() {
        return this.publishYear;
    }

    public boolean getCheckOutStatus() {
        return this.isAvailStatus;
    }

    public String listBookDetail() {
        return (this.bookDetails != null) ? bookDetails.substring(0, 56) : null;
    }

    public String listAllBookDetail() {
        return (this.bookDetails != null) ? bookDetails : null;
    }

    public void checkOutBook() {
        if (this.title != null && this.author != null && this.publishYear != 0) {
            this.isAvailStatus = false;
            formBookDetails();
        }
    }

    public void returnBook() {
        this.isAvailStatus = true;
        formBookDetails();
    }
}
