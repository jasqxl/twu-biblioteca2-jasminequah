package com.twu.biblioteca;

import java.util.List;
import java.util.Scanner;

public class BibliotecaApp {

    private static String invalidMenuOptionMessage = "Select a valid option!\n";
    //private static String actionMessage = "What would you like to do?\n";

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

    public static void doAction(String userChoice, List<String> options) {
        if (options.get(Integer.parseInt(userChoice) - 1) == "List Books") {
            if (BookList.getBookList().size() == 0) {
                checkUserChoice(userChoice, options);
            }
            else {
                BookList.listBooks();
                //System.out.println(actionMessage);
            }
        }
        return;
    }

    private static int parseOption(String userChoice, List<String> options) {
        if (userChoice.length() >= 4 && userChoice.toLowerCase().indexOf("quit") >= 0) return 0;
        return isNumericAndValid(userChoice, options);
    }

    private static int isNumericAndValid(String userChoice, List<String> options) {
        for (int i = 0; i < userChoice.length(); i++) {
            if (userChoice.charAt(i) < '0' || userChoice.charAt(i) > '9') return -1;
        }

        if (Integer.parseInt(userChoice) < 1 || Integer.parseInt(userChoice) > options.size()) return -1;
        return (options.get(Integer.parseInt(userChoice) - 1) != null) ? Integer.parseInt(userChoice) : -1;
    }
}
