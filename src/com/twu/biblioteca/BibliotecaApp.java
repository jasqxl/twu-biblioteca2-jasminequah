package com.twu.biblioteca;

import java.util.*;

public class BibliotecaApp {

    private static String loginMessage = "To check out or return, please login first.\n";
    private static String checkOutMessage = "Please enter index of item to check out: ";
    private static String returnMessage = "Please enter index number of item to return: ";
    private static String enterAccountNumberMessage = "Account number: ";
    private static String enterPasswordMessage = "Password: ";
    private static String invalidMenuOptionMessage = "Select a valid option!\n";
    private static String actionMessage = "What would you like to do?\n";

    private static BookList bookList = new BookList();
    private static MovieList movieList = new MovieList();
    private static List<String> accountDetails = new ArrayList<String>();
    private static String input = "0";

    public static void main(String[] args) {
        Menu.openProgram();
        loopUserInputUntilQuit(Menu.getMenu(), "");
        Menu.closeProgram();
    }

    private static void loopUserInputUntilQuit(List<String> menu, String whatMedia) {
        do {
            if (menu.get(0).equals("List Books")) Menu.showMenu();
            else if(menu.get(0).equals("Check out item")) {
                System.out.println(actionMessage);
                Menu.showActionMenu();
            }

            input = Integer.toString(checkUserChoice(getUserInput(), menu.size()));

            if (Integer.parseInt(input) > 0) {
                if (menu.get(0).equals("List Books")) doAction(input, menu);
                else if (menu.get(0).equals("Check out item")) {
                    doUserAction(input, menu, whatMedia);
                    return;
                }
            }
        } while (Integer.parseInt(input) != 0);
    }

    private static String getUserInput() {
        Scanner reader = new Scanner(System.in);
        return reader.nextLine().trim();
    }

    public static int checkUserChoice(String userChoice, int sizeOfList) {
        if (parseOption(userChoice, sizeOfList) == 0) return 0;
        if (parseOption(userChoice, sizeOfList) != -1) return parseOption(userChoice, sizeOfList);
        else invalidOptionSelected();
        return -1;
    }

    private static void invalidOptionSelected() {
        System.out.println(invalidMenuOptionMessage);
    }

    private static int parseOption(String userChoice, int sizeOfList) {
        if (isQuit(userChoice)) return 0;
        if (!isNumeric(userChoice)) return -1;
        if (isOptionValid(userChoice, sizeOfList)) return Integer.parseInt(userChoice);
        return -1;
    }

    private static boolean isQuit(String userChoice) {
        if (userChoice.trim().toLowerCase().equals("quit")) return true;
        return false;
    }

    private static boolean isNumeric(String userChoice) {
        for (int i = 0; i < userChoice.length(); i++) {
            if (userChoice.charAt(i) < '0' || userChoice.charAt(i) > '9') return false;
        }
        return true;
    }

    private static boolean isOptionValid(String userChoice, int sizeOfList) {
        if (userChoice.trim().length() == 0) return false;
        if (Integer.parseInt(userChoice) < 1 || Integer.parseInt(userChoice) > sizeOfList) return false;
        return true;
    }

    private static void userLoginForListBook() {
        System.out.println(loginMessage);
        login();

        System.out.println("Account Type: " + accountDetails.get(5) + "\nWelcome back, " + accountDetails.get(0));
        if (accountDetails.get(5).equals("User")) {
            System.out.println(getUserInfo());
            loopUserInputUntilQuit(Menu.getActionMenu(), "book");
        }
        else System.out.println(getCheckedOutBook());
    }

    private static void login () {
        String accountNumber, password;

        System.out.print(enterAccountNumberMessage);
        accountNumber = getUserInput();
        System.out.print(enterPasswordMessage);
        password = getUserInput();

        accountDetails = Account.loginAccount(accountNumber, password);
        if (accountDetails.size() != 6) login();
    }

    private static String getUserInfo() {
        return  "\nEmail: " + accountDetails.get(1) +
                "\nContact number: " + accountDetails.get(2) + "\n";
    }

    public static String getCheckedOutBook() {
        return bookList.checkedOutItemList();
    }

    public static void doAction(String userChoice, List<String> options) {
        if (options.get(Integer.parseInt(userChoice) - 1).equals("List Books")) {
            System.out.println("\n" + bookList.listItems(bookList.getList()));
            userLoginForListBook();

        }
        else if (options.get(Integer.parseInt(userChoice) - 1).equals("List Movies")) {
            System.out.println("\n" + movieList.listItems(movieList.getList()));
            loopUserInputUntilQuit(Menu.getActionMenu(), "movie");
        }
    }

    public static void doUserAction(String userChoice, List<String> options, String whatMedia) {
        if (options.get(Integer.parseInt(userChoice) - 1).equals("Check out item")) {
            checkOut(checkOutMessage, whatMedia, bookList, movieList);
        }
        else if (options.get(Integer.parseInt(userChoice) - 1).equals("Return item")) {
            returnItem(returnMessage, whatMedia, bookList, movieList);
        }
    }

    private static void checkOut(String message, String whatMedia, BookList bookList, MovieList movieList) {
        do {
            if (whatMedia.equals("book") && bookList.getAvailableList().size() != 0) {
                System.out.print(message);
                input = Integer.toString(checkUserChoice(getUserInput(), bookList.getAvailableList().size()));
            }
            else if (whatMedia.equals("movie") && movieList.getAvailableList().size() != 0) {
                System.out.print(message);
                input = Integer.toString(checkUserChoice(getUserInput(), movieList.getAvailableList().size()));
            }

            if (Integer.parseInt(input) > 0) {
                if (whatMedia.equals("book")) bookList.checkOutAnItem(Integer.parseInt(input), accountDetails.get(3));
                else if (whatMedia.equals("movie")) movieList.checkOutAnItem(Integer.parseInt(input), "");
            }
        } while (Integer.parseInt(input) < 0);
    }

    private static void returnItem(String message, String whatMedia, BookList bookList, MovieList movieList) {
        do {
            if (whatMedia.equals("book")) bookList.printList(bookList.getUnavailableList());
            else if (whatMedia.equals("movie")) movieList.printList(movieList.getUnavailableList());

            if (movieList.getUnavailableList().size() > 0) {
                System.out.print(message);
                input = Integer.toString(checkUserChoice(getUserInput(), movieList.getUnavailableList().size()));

                if (Integer.parseInt(input) > 0) {
                    if (whatMedia.equals("book")) bookList.returnAnItem(Integer.parseInt(input));
                    else if (whatMedia.equals("movie")) movieList.returnAnItem(Integer.parseInt(input));
                }
            }
        } while (Integer.parseInt(input) < 0);
    }
}
