package com.twu.biblioteca;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MovieList implements MediaList <Movie> {

    private String movieListHeader = "S/N  |" + String.format("%-30s", "Movie Title") + "|" + String.format("%-20s", "Director") + "|Year |Rating";
    private String successfulCheckOutMessage = "Thank you! Enjoy the movie.\n";
    private String unsuccessfulCheckOutMessage = "That movie is not available.\n";
    private String successfulReturnMessage = "Thank you for returning the movie.\n";
    private String unsuccessfulReturnMessage = "That is not a valid movie to return.\n";
    private String emptyMovieListMessage = "There are no available movies right now, please try again later..\n";

    private static String workingFilePath = System.getProperty("user.dir") + "/Movie List.txt";

    private List<Movie> movieList = new ArrayList<Movie>();
    private List<String> allMovieListDetail = new ArrayList<String>();

    private String fileName = "Movie List.txt";
    private int[] movieSerialNumberArray;
    private int numberOfAvailableMovies;
    private String listOfMovies;

    public String listItems() {
        listOfMovies = "";
        numberOfAvailableMovies = 0;

        if (movieList.size() == 0) {
            retrieveList();
            if (movieList.size() == 0) listOfMovies = emptyMovieListMessage;
        } else {
            listOfMovies = listOfMovies + movieListHeader + "\n";
            int movieSerialNumber = 1;
            movieSerialNumberArray = new int[movieList.size()];

            for (int i = 0; i < movieList.size(); i++) {
                if (movieList.get(i).getCheckOutStatus()) {
                    numberOfAvailableMovies++;
                    movieSerialNumberArray[movieSerialNumber - 1] = i;
                    listOfMovies = listOfMovies + String.format("%-5d", movieSerialNumber) + "|" + movieList.get(i).listDetail() + "\n";
                    movieSerialNumber++;
                }
            }
        }
        return listOfMovies;
    }

    private void retrieveList () {
        movieList.clear();
        
        File tmpDir = new File(workingFilePath);

        if (tmpDir.exists()) allMovieListDetail = FileStream.readFromFile(fileName, allMovieListDetail);

        for (int i = 0; i < allMovieListDetail.size(); i++) {
            Movie newMovie = new Movie(allMovieListDetail.get(i));
            movieList.add(newMovie);
            if (movieList.get(movieList.size()-1).getCheckOutStatus()) numberOfAvailableMovies++;
        }
        
        movieSerialNumberArray = null;
        if (!(allMovieListDetail.size() == 0 && movieList.size() == 0)) listOfMovies = listItems();
    }

    public void addToList(Movie newMovie) {
        allMovieListDetail.add(newMovie.listAllDetail());
        FileStream.appendItemToFile(fileName, allMovieListDetail);
        retrieveList();
    }

    public void removeItem(String title, String creator, int publishYear) {
        for (int i = 0; i < allMovieListDetail.size(); i++) {
            if (movieList.get(i).getTitle().toLowerCase().indexOf(title.toLowerCase()) != -1 &&
                    movieList.get(i).getCreator().toLowerCase().indexOf(creator.toLowerCase()) != -1 &&
                    movieList.get(i).getReleaseYear() == publishYear) {
                allMovieListDetail.remove(i);
                i = allMovieListDetail.size();
            }
        }

        FileStream.removeItemsFromFile(fileName, allMovieListDetail);
        retrieveList();
    }

    public void removeAllItems() {
        movieList.clear();
        allMovieListDetail.clear();
        movieSerialNumberArray = null;
        listOfMovies = null;
        numberOfAvailableMovies = 0;

        FileStream.removeItemsFromFile(fileName, allMovieListDetail);
    }

    public void checkOutAnItem(int serial, String accountNumberOfBorrower) {
        movieSerialNumberArray = null;
        listOfMovies = listItems();

        if (serial < 1 || serial > numberOfAvailableMovies) {
            System.out.println(unsuccessfulCheckOutMessage);
        }
        else if (movieList.get(movieSerialNumberArray[serial - 1]) != null &&
                movieList.get(movieSerialNumberArray[serial - 1]).getCheckOutStatus()) {
            movieList.get(movieSerialNumberArray[serial - 1]).checkOutItem(accountNumberOfBorrower);
            System.out.println(successfulCheckOutMessage);
        }
        else {
            System.out.println(unsuccessfulCheckOutMessage);
        }

        movieSerialNumberArray = null;
        listOfMovies = listItems();
    }

    public void returnAnItem(String title, String creator, int publishYear) {
        Boolean isReturned = false;

        for (int i = 0; i < movieList.size(); i++) {
            if (movieList.get(i).getTitle().toLowerCase().indexOf(title.toLowerCase()) != -1 &&
                    movieList.get(i).getCreator().toLowerCase().indexOf(creator.toLowerCase()) != -1 &&
                    movieList.get(i).getReleaseYear() == publishYear &&
                    movieList.get(i).getCheckOutStatus() == false) {
                movieList.get(i).returnItem();
                isReturned = true;
                System.out.println(successfulReturnMessage);
                i = movieList.size();
            }
        }

        if (!isReturned) {
            System.out.println(unsuccessfulReturnMessage);
        }

        movieSerialNumberArray = null;
        listOfMovies = listItems();
    }

    public List<Movie> getList() {
        return movieList;
    }
}
