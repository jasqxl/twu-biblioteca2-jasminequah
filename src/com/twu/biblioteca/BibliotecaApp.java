package com.twu.biblioteca;

import java.util.*;

public class BibliotecaApp {

    private static String loginMessage = "To check out or return a book, please login first.\n";
    private static String enterAccountNumberMessage = "Account number: ";
    private static String enterPasswordMessage = "Password: ";
    private static String invalidMenuOptionMessage = "Select a valid option!\n";
    private static String actionMessage = "What would you like to do?\n";

    private static BookList bookList = new BookList();
    private static MovieList movieList = new MovieList();
    private static List<String> accountDetails = new ArrayList<String>();
    private static String userChoice = "-1";
    private static String input = "0";

    public static void main(String[] args) {
        Menu.openProgram();
        loopUserInputUntilQuit(Menu.getMenu());
        Menu.closeProgram();
    }

    private static void loopUserInputUntilQuit(List<String> menu) {
        do {
            if (input.toLowerCase().indexOf("quit") == -1) {

                if (menu.get(0).equals("List Books")) Menu.showMenu();
                else Menu.showActionMenu();

                input = getUserInput();
                input = Integer.toString(checkUserChoice(userChoice, menu));
                if (!input.equals(0) && menu.get(0).equals("List Books")) doAction(userChoice, menu);
                else if (!input.equals(0) && menu.get(0).equals("Check out item")) doUserAction(userChoice, menu);
            }
        } while (input.toLowerCase().indexOf("quit") == -1);
    }

    private static String getUserInput() {
        Scanner reader = new Scanner(System.in);
        userChoice = reader.nextLine();
        return userChoice;
    }

    public static int checkUserChoice(String userChoice, List<String> options) {
        if (parseOption(userChoice, options) == 0) {}
        if (parseOption(userChoice, options) != -1) return parseOption(userChoice, options);
        else invalidOptionSelected();
        return 0;
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

    private static void login () {
        String accountNumber, password;

        System.out.print(enterAccountNumberMessage);
        accountNumber = getUserInput();
        System.out.print(enterPasswordMessage);
        password = getUserInput();

        accountDetails = Account.loginAccount(accountNumber, password);
        System.out.println("Account Type: " + accountDetails.get(5));
        if (accountDetails.size() != 6) login();
    }

    private static String getUserInfo() {
        return  "Name: " + accountDetails.get(0) +
                "\nEmail: " + accountDetails.get(1) +
                "\nContact number: " + accountDetails.get(2);
    }

    public static String getCheckedOutBook() {
        return "";
    }

    public static void doAction(String userChoice, List<String> options) {
        if (options.get(Integer.parseInt(userChoice) - 1).equals("List Books")) {

            System.out.println(bookList.listItems());
            System.out.println(loginMessage);
            login();

            if (accountDetails.get(5).equals("User")) System.out.println(getUserInfo());
            else System.out.println(getCheckedOutBook());

            System.out.println(actionMessage);
            loopUserInputUntilQuit(Menu.getActionMenu());
        }
        else if (options.get(Integer.parseInt(userChoice) - 1).equals("List Movies")) {

            System.out.println(movieList.listItems());
            System.out.println(actionMessage);
            loopUserInputUntilQuit(Menu.getActionMenu());
        }
    }

    public static void doUserAction(String userChoice, List<String> options) {
        if (options.get(Integer.parseInt(userChoice) - 1).equals("Check out item")) {

        }
        else if (options.get(Integer.parseInt(userChoice) - 1).equals("Return item")) {

        }
    }
}
