package com.twu.biblioteca;

public class Movie implements Media {

    private String title;
    private String director;
    private int releaseYear;
    private double rating;
    private boolean isAvailStatus = true;
    private String movieDetails;

    public Movie() {}

    public Movie(String title, String director, int releaseYear, boolean isAvailStatus, double rating) {
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
        this.isAvailStatus = isAvailStatus;
        this.rating = rating;
        formDetails();
    }

    public Movie(String movieDetails) {
        this.title = movieDetails.substring(0, 30).trim();
        this.director = movieDetails.substring(31, 51).trim();
        this.releaseYear = Integer.parseInt(movieDetails.substring(52, 57).trim());
        this.rating = Double.parseDouble(movieDetails.substring(58, 65).trim());

        if (movieDetails.substring(66, movieDetails.length()).toLowerCase().indexOf("unavailable") != -1) {
            this.isAvailStatus = false;
        }
        else if(movieDetails.substring(66, movieDetails.length()).toLowerCase().indexOf("available") != -1)  {
            this.isAvailStatus = true;
        }
        formDetails();
    }

    private void formDetails() {
        String movieStatus;
        if (this.isAvailStatus) movieStatus = "Available";
        else movieStatus = "Unavailable";
        
        this.movieDetails = String.format("%-30s", title) + "|" +
                String.format("%-20s", director) + "|" +
                String.format("%-5s", Integer.toString(releaseYear)) + "|" +
                String.format("%-7s", Double.toString(rating)) + "|" +
                movieStatus;
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
        return (this.movieDetails != null) ? movieDetails.substring(0, 65).trim() : null;
    }

    public String listAllDetail() {
        return (this.movieDetails != null) ? movieDetails : null;
    }

    public void checkOutItem() {
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
