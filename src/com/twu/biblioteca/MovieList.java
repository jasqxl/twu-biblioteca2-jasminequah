package com.twu.biblioteca;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MovieList implements MediaList <Movie> {

    private String movieListHeader = "S/N  |" + String.format("%-30s", "Movie Title") + "|" + String.format("%-20s", "Director") + "|Year |Rating\n";
    private String successfulCheckOutMessage = "Thank you! Enjoy the movie.\n";
    private String unsuccessfulCheckOutMessage = "That movie is not available.\n";
    private String successfulReturnMessage = "Thank you for returning the movie.\n";
    private String unsuccessfulReturnMessage = "That is not a valid movie to return.\n";
    private String emptyMovieListMessage = "There are no available movies right now, please try again later..\n";

    private static String workingFilePath = System.getProperty("user.dir") + "/Movie List.txt";

    private List<Movie> movieList = new ArrayList<Movie>();
    private List<Movie> availableMovieList = new ArrayList<Movie>();
    private List<Movie> unavailableMovieList = new ArrayList<Movie>();
    private List<String> allMovieListDetail = new ArrayList<String>();

    private String fileName = "Movie List.txt";
    private int[] availableMovieArray;
    private int[] unavailableMoviesArray;
    private int numberOfAvailableMovies;
    private int numberOfUnavailableMovies;
    private String listOfMovies;

    public String listItems(List <Movie> movieList) {
        listOfMovies = "";
        numberOfAvailableMovies = 0;
        numberOfUnavailableMovies = 0;
        availableMovieList.clear();
        unavailableMovieList.clear();
        availableMovieArray = null;
        unavailableMoviesArray = null;

        if (movieList.size() == 0) {
            retrieveList();
            if (movieList.size() == 0) listOfMovies = emptyMovieListMessage;
        } else {
            listOfMovies = listOfMovies + movieListHeader;
            availableMovieArray = new int[movieList.size()];
            unavailableMoviesArray = new int[movieList.size()];

            for (int i = 0; i < movieList.size(); i++) {
                if (movieList.get(i).getCheckOutStatus()) {
                    numberOfAvailableMovies++;
                    availableMovieList.add(movieList.get(i));
                    availableMovieArray[numberOfAvailableMovies - 1] = i;
                    listOfMovies = listOfMovies + detailWithSerialNumber(numberOfAvailableMovies,movieList.get(i).listDetail());
                }
                else {
                    numberOfUnavailableMovies++;
                    unavailableMovieList.add(movieList.get(i));
                    unavailableMoviesArray[numberOfUnavailableMovies - 1] = i;
                }
            }
        }
        return listOfMovies;
    }


    public String detailWithSerialNumber(int serial, String detail) {
        return String.format("%-5d", serial) + "|" + detail;
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
        
        if (!(allMovieListDetail.size() == 0 && movieList.size() == 0)) listOfMovies = listItems(movieList);
    }

    public void addToList(Movie newMovie) {
        allMovieListDetail.add(newMovie.listAllDetail());
        FileStream.writeToFile(fileName, allMovieListDetail);
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

        FileStream.removeItemsFromFile(fileName, allMovieListDetail);
        retrieveList();
    }

    public void checkOutAnItem(int serial, String accountNumberOfBorrower) {
        listOfMovies = listItems(movieList);

        if (serial < 1 || serial > numberOfAvailableMovies) {
            System.out.println(unsuccessfulCheckOutMessage);
        }
        else if (movieList.get(availableMovieArray[serial - 1]) != null &&
                movieList.get(availableMovieArray[serial - 1]).getCheckOutStatus()) {
            movieList.get(availableMovieArray[serial - 1]).checkOutItem(accountNumberOfBorrower);
            allMovieListDetail.set(availableMovieArray[serial - 1], movieList.get(availableMovieArray[serial - 1]).listAllDetail());
            System.out.println(successfulCheckOutMessage);
        }
        else {
            System.out.println(unsuccessfulCheckOutMessage);
        }
        listItems(movieList);
        FileStream.writeToFile(fileName, allMovieListDetail);
        retrieveList();
    }

    public void returnAnItem(int serial) {
        if (serial < 1 || serial > numberOfUnavailableMovies) {
            System.out.println(unsuccessfulReturnMessage);
        }
        else if (movieList.get(unavailableMoviesArray[serial - 1]) != null &&
                !movieList.get(unavailableMoviesArray[serial - 1]).getCheckOutStatus()) {
            movieList.get(unavailableMoviesArray[serial - 1]).returnItem();
            allMovieListDetail.set(unavailableMoviesArray[serial - 1], movieList.get(unavailableMoviesArray[serial - 1]).listAllDetail());
            System.out.println(successfulReturnMessage);
        }
        else System.out.println(unsuccessfulReturnMessage);

        listItems(movieList);
        FileStream.writeToFile(fileName, allMovieListDetail);
        retrieveList();
    }

    public List<Movie> getList() {
        return movieList;
    }

    public List<Movie> getAvailableList() {
        return availableMovieList;
    }

    public List<Movie> getUnavailableList() {
        return unavailableMovieList;
    }
}
