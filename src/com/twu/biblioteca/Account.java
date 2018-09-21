package com.twu.biblioteca;

import java.util.ArrayList;
import java.util.List;

public class Account {

    private static String unsuccessfulLoginMessage = "The account number and password provided does not exist.\n";
    private static String successfulLoginMessage = "You have successfully logged into Biblioteca.\n";

    private static String userFileName = "User Account List.txt";
    private static String librarianFileName = "Librarian Account List.txt";

    private static List<String> accountDetails = new ArrayList<String>();

    public static List<String> loginAccount(String accountNumber, String password) {
        accountDetails = FileStream.findAccountFromFile(userFileName, accountNumber, password);

        if (accountDetails.size() != 5) {
            accountDetails = FileStream.findAccountFromFile(librarianFileName, accountNumber, password);

            if (accountDetails.size() != 5) {
                System.out.println(unsuccessfulLoginMessage);
            }
            else {
                addUserType("Librarian");
                System.out.println(successfulLoginMessage);
            }
        }
        else {
            addUserType("User");
            System.out.println(successfulLoginMessage);
        }

        return accountDetails;
    }

    private static void addUserType(String userType) {
        if (accountDetails.size() == 5) {
            accountDetails.add(userType);
        }
    }
}
