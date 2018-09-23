package com.twu.biblioteca;

public class Book implements Media {

    private String title;
    private String author;
    private int publishYear;
    private boolean isAvailStatus = true;
    private String bookDetails;
    private String accountNumberOfBorrower = "";

    public Book() {}

    public Book(String title, String author, int publishYear, String accountNumber) {
        this.title = title;
        this.author = author;
        this.publishYear = publishYear;
        this.accountNumberOfBorrower = accountNumber;
        if (this.accountNumberOfBorrower.length() != 0) this.isAvailStatus = false;
        formDetails();
    }

    public Book(String bookDetails) {
        this.title = bookDetails.substring(0, 30).trim();
        this.author = bookDetails.substring(31, 51).trim();
        this.publishYear = Integer.parseInt(bookDetails.substring(52, 57).trim());

        if (bookDetails.substring(58, bookDetails.length()).trim().length() > 0) {
            this.isAvailStatus = false;
            this.accountNumberOfBorrower = bookDetails.substring(58, bookDetails.length()).trim();
        }
        else this.isAvailStatus = true;

        formDetails();
    }

    private void formDetails() {
        this.bookDetails = String.format("%-30s", this.title) + "|" +
                String.format("%-20s", this.author) + "|" +
                String.format("%-5s", Integer.toString(this.publishYear)) + "|" +
                this.accountNumberOfBorrower;
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

    public String getBorrowerAccountNumber() {
        return this.accountNumberOfBorrower;
    }

    public boolean getCheckOutStatus() {
        return this.isAvailStatus;
    }

    public String listDetail() {
        return (this.bookDetails != null) ? bookDetails.substring(0, 57).trim() + "\n" : null;
    }

    public String listAllDetail() {
        return (this.bookDetails != null) ? bookDetails + "\n" : null;
    }

    public void checkOutItem(String accountNumber) {
        if (this.title != null && this.author != null && this.publishYear != 0) {
            this.isAvailStatus = false;
            this.accountNumberOfBorrower = accountNumber;
            formDetails();
        }
    }

    public void returnItem() {
        this.isAvailStatus = true;
        this.accountNumberOfBorrower = "";
        formDetails();
    }
}
