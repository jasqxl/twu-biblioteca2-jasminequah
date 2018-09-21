package com.twu.biblioteca;

public class Book implements Media {

    private String title;
    private String author;
    private int publishYear;
    private boolean isAvailStatus = true;
    private String bookDetails;
    private int libraryNumberOfBorrower = 0;

    public Book() {}

    public Book(String title, String author, int publishYear, boolean isAvailStatus) {
        this.title = title;
        this.author = author;
        this.publishYear = publishYear;
        this.isAvailStatus = isAvailStatus;
        formDetails();
    }

    public Book(String bookDetails) {
        this.title = bookDetails.substring(0, 30).trim();
        this.author = bookDetails.substring(31, 51).trim();
        this.publishYear = Integer.parseInt(bookDetails.substring(52, 57).trim());

        if (bookDetails.substring(58, bookDetails.length()).toLowerCase().indexOf("unavailable") != -1) {
            this.isAvailStatus = false;
        }
        else if(bookDetails.substring(58, bookDetails.length()).toLowerCase().indexOf("available") != -1)  {
            this.isAvailStatus = true;
        }
        formDetails();
    }

    private void formDetails() {
        String bookStatus;
        if (this.isAvailStatus) bookStatus = "Available";
        else bookStatus = "Unavailable";

        this.bookDetails = String.format("%-30s", title) + "|" +
                String.format("%-20s", author) + "|" +
                String.format("%-5s", Integer.toString(publishYear)) + "|" +
                bookStatus;
    }

    public String getTitle() {
        return this.title;
    }

    public String getCreator() {
        return this.author;
    }

    public int getReleaseYear() {
        return this.publishYear;
    }

    public int getBorrowerLibraryNumber() {
        return this.libraryNumberOfBorrower;
    }

    public boolean getCheckOutStatus() {
        return this.isAvailStatus;
    }

    public String listDetail() {
        return (this.bookDetails != null) ? bookDetails.substring(0, 57).trim() : null;
    }

    public String listAllDetail() {
        return (this.bookDetails != null) ? bookDetails : null;
    }

    public void checkOutItem(int libraryNumber) {
        if (this.title != null && this.author != null && this.publishYear != 0) {
            this.isAvailStatus = false;
            this.libraryNumberOfBorrower = libraryNumber;
            formDetails();
        }
    }

    public void returnItem() {
        this.isAvailStatus = true;
        this.libraryNumberOfBorrower = 0;
        formDetails();
    }

    public void recordBorrowerLibraryNumber(int libraryNumberOfBorrower) {
        this.libraryNumberOfBorrower = libraryNumberOfBorrower;
    }
}
