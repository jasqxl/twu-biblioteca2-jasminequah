package com.twu.biblioteca;

import java.io.*;
import java.util.*;

public class Menu {

    private static String welcomeMessage = "Welcome to Biblioteca :)\n";
    private static String goodbyeMessage = "Thank you for using Biblioteca..\n";
    private static String menuHeading = "Please choose an action from the list below:\n";

    private static List<String> necessaryOptions = new ArrayList<String>();
    private static List<String> actionOptions = new ArrayList<String>();
    private static List<String> options = new ArrayList<String>();
    private static String fileName = "options.txt";
    private static String workingOptionFilePath = System.getProperty("user.dir") + "/options.txt";

    public static void openProgram() {
        necessaryOptions.clear();
        necessaryOptions.add("List Books");
        necessaryOptions.add("List Movies");
        options = necessaryOptions;
        actionOptions.clear();
        actionOptions.add("Check out item");
        actionOptions.add("Return item");

        System.out.println(welcomeMessage);
    }

    public static void closeProgram() {
        System.out.println(goodbyeMessage);
    }

    public static void showMenu () {
        System.out.println(menuHeading);

        File tmpDir = new File(workingOptionFilePath);
        if (tmpDir.exists() && options.size() == 0) options = FileStream.readFromFile(fileName, necessaryOptions);
        else if (!tmpDir.exists()) {
            options = necessaryOptions;
            FileStream.writeToFile(fileName, necessaryOptions);
        }

        printMenu (options);
        FileStream.writeToFile(fileName, options);
        System.out.print("\nEnter number of choice here: ");
    }

    public static void showActionMenu () {
        printMenu(actionOptions);
        System.out.print("\nEnter number of choice here: ");
    }

    private static void printMenu(List<String> menu) {
        for (int i = 0; i < menu.size(); i++) {
            System.out.println(i+1 + ") " + menu.get(i));
        }
    }

    public static void addOptionsToFile(String newOption) {
        if (newOption.length() != 0) {
            options.add(newOption);
            FileStream.writeToFile(fileName, options);
        }
    }

    public static void removeOption(String optionToRemove) {
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i).toLowerCase().indexOf((optionToRemove).toLowerCase()) != -1) {
                options.remove(i);
                i = options.size();
            }
        }
        FileStream.removeItemsFromFile(fileName, options);
    }

    public static void removeAllOptions() {
        options.clear();
        options = necessaryOptions;
        FileStream.removeItemsFromFile(fileName, options);
    }

    public static List<String> getMenu() {
        return options;
    }

    public static List<String> getActionMenu() {
        return actionOptions;
    }
}
