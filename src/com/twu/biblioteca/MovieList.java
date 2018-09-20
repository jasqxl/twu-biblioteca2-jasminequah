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

    private List<Movie> movieList = new ArrayList<Movie>();

    private String fileName = "Movie List.txt";
    private String line = null;
    private int[] movieSerialNumberArray;
    private int numberOfAvailableMovies;
    private String listOfMovies;

    public String listItems() {
        listOfMovies = "";

        if (movieList.size() == 0) {
            listOfMovies = emptyMovieListMessage;
        } else {
            listOfMovies = listOfMovies + movieListHeader + "\n";
            int movieSerialNumber = 1;
            movieSerialNumberArray = new int[movieList.size()];
            numberOfAvailableMovies = 0;

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

    public void retrieveList () {
        movieList.clear();
        numberOfAvailableMovies = 0;

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                Movie newMovie = new Movie(line);
                movieList.add(newMovie);
                if (movieList.get(movieList.size()-1).getCheckOutStatus()) numberOfAvailableMovies++;
            }
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }

        movieSerialNumberArray = null;
        listOfMovies = listItems();
    }

    public void addToList(Movie newMovie) {
        try (FileWriter fileWriter = new FileWriter(fileName, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {
            printWriter.println(newMovie.listAllDetail());
        }
        catch(IOException ex1) {
            System.out.println("Error reading file '" + fileName + "'");
        }
        retrieveList();
    }

    public void removeItem(String title, String creator, int publishYear) {
        for (int i = 0; i < movieList.size(); i++) {
            if (movieList.get(i).getTitle().toLowerCase().indexOf(title.toLowerCase()) != -1 &&
                    movieList.get(i).getCreator().toLowerCase().indexOf(creator.toLowerCase()) != -1 &&
                    movieList.get(i).getReleaseYear() == publishYear) {
                movieList.remove(i);
                i = movieList.size();
            }
        }

        try (FileWriter fileWriter = new FileWriter(fileName, false);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {

            for (int i = 0; i < movieList.size(); i++) {
                printWriter.println(movieList.get(i).listAllDetail());
            }
        }
        catch(IOException ex1) {
            System.out.println("Error reading file '" + fileName + "'");
        }
        retrieveList();
    }

    public void removeAllItems() {
        movieList.clear();
        movieSerialNumberArray = null;
        listOfMovies = null;
        numberOfAvailableMovies = 0;

        try (FileWriter fileWriter = new FileWriter(fileName, false);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {
            printWriter.print("");
        }
        catch(IOException ex1) {
            System.out.println("Error reading file '" + fileName + "'");
        }
    }

    public void checkOutAnItem(int serial) {
        movieSerialNumberArray = null;
        listOfMovies = listItems();

        if (serial < 1 || serial > numberOfAvailableMovies) {
            System.out.println(unsuccessfulCheckOutMessage);
        }
        else if (movieList.get(movieSerialNumberArray[serial - 1]) != null &&
                movieList.get(movieSerialNumberArray[serial - 1]).getCheckOutStatus()) {
            movieList.get(movieSerialNumberArray[serial - 1]).checkOutItem();
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
