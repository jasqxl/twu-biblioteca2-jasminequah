package com.twu.biblioteca;

public class Movie implements Media {

    private String title;
    private String director;
    private int releaseYear;
    private double rating;
    private boolean isAvailStatus = true;
    private String movieDetails;

    public Movie() {}

    public Movie(String title, String director, int releaseYear, boolean availableStatus, double rating) {
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
        this.isAvailStatus = availableStatus;
        this.rating = rating;
        formDetails();
    }

    public Movie(String movieDetails) {
        this.title = movieDetails.substring(0, 30).trim();
        this.director = movieDetails.substring(31, 51).trim();
        this.releaseYear = Integer.parseInt(movieDetails.substring(52, 57).trim());
        this.rating = Double.parseDouble(movieDetails.substring(58, 65).trim());

        if (movieDetails.substring(66, movieDetails.length()).trim().toLowerCase().equals("unavailable")) this.isAvailStatus = false;
        else if (movieDetails.substring(66, movieDetails.length()).trim().toLowerCase().equals("available")) this.isAvailStatus = true;
        formDetails();
    }

    private void formDetails() {
        String availability = "";
        if (this.isAvailStatus) availability = "available";
        else availability = "unavailable";
        
        this.movieDetails = String.format("%-30s", this.title) + "|" +
                String.format("%-20s", this.director) + "|" +
                String.format("%-5s", Integer.toString(this.releaseYear)) + "|" +
                String.format("%-7s", Double.toString(this.rating)) + "|" +
                availability;
    }

    public String getTitle() {
        return this.title;
    }

    public String getCreator() {
        return this.director;
    }

    public int getReleaseYear() {
        return this.releaseYear;
    }

    public boolean getCheckOutStatus() {
        return this.isAvailStatus;
    }

    public String listDetail() {
        return (this.movieDetails != null) ? movieDetails.substring(0, 65).trim() + "\n": null;
    }

    public String listAllDetail() {
        return (this.movieDetails != null) ? movieDetails + "\n" : null;
    }

    public void checkOutItem(String accountNumber) {
        if (this.title != null && this.director != null && this.releaseYear != 0) {
            this.isAvailStatus = false;
            formDetails();
        }
    }

    public void returnItem() {
        this.isAvailStatus = true;
        formDetails();
    }
}
