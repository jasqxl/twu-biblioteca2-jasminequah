package com.twu.biblioteca;

import java.util.List;
import java.util.Scanner;

public class BibliotecaApp {

    private String bookListHeader = "S/N  |" + String.format("%-30s", "Book Title") + "|" + String.format("%-20s", "Author") + "|Year";
    private String movieListHeader = "S/N  |" + String.format("%-30s", "Movie Title") + "|" + String.format("%-20s", "Director") + "|Year |Rating";
    private static String invalidMenuOptionMessage = "Select a valid option!\n";
    //private static String actionMessage = "What would you like to do?\n";

    private static BookList bookList = new BookList();
    private static MovieList movieList = new MovieList();
    private static String userChoice = "-1";
    private static String input;

    public static void main(String[] args) {

        Menu.openProgram();

        do {
            input = getUserChoice();
            checkUserChoice(userChoice, Menu.getOptions());
            if (input.toLowerCase().indexOf("quit") == -1) {
                Menu.showMenu();
            }
        } while (input.toLowerCase().indexOf("quit") == -1);

        Menu.closeProgram();
    }

    private static String getUserChoice() {
        Scanner reader = new Scanner(System.in);
        userChoice = reader.nextLine();
        return userChoice;
    }

    public static void checkUserChoice(String userChoice, List<String> options) {
        if (parseOption(userChoice, options) == 0) return;
        if (parseOption(userChoice, options) != -1); // doAction(userChoice, options);
        else invalidOptionSelected();
    }

    private static void invalidOptionSelected() {
        System.out.println(invalidMenuOptionMessage);
    }

    private static int parseOption(String userChoice, List<String> options) {
        if (isQuit(userChoice, options)) return 0;
        if (!isNumeric(userChoice, options)) return -1;
        if (isOptionValid(userChoice, options)) return Integer.parseInt(userChoice);
        return -1;
    }

    private static boolean isQuit(String userChoice, List<String> options) {
        if (userChoice.length() >= 4 && userChoice.toLowerCase().indexOf("quit") >= 0) return true;
        return false;
    }

    private static boolean isNumeric(String userChoice, List<String> options) {
        for (int i = 0; i < userChoice.length(); i++) {
            if (userChoice.charAt(i) < '0' || userChoice.charAt(i) > '9') return false;
        }
        return true;
    }

    private static boolean isOptionValid(String userChoice, List<String> options) {
        if (Integer.parseInt(userChoice) < 1 || Integer.parseInt(userChoice) > options.size()) return false;
        return (options.get(Integer.parseInt(userChoice) - 1) != null) ? true : false;
    }

    public static void doAction(String userChoice, List<String> options) {
        if (options.get(Integer.parseInt(userChoice) - 1) == "List Books") {
            bookList.listItems();
            //System.out.println(actionMessage);
        }
        else if (options.get(Integer.parseInt(userChoice) - 1) == "List Movies") {
            movieList.listItems();
            //System.out.println(actionMessage);
        }
        return;
    }
}
