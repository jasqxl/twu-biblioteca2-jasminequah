package com.twu.biblioteca;

import java.io.*;
import java.util.*;

public class Menu {

    private static String welcomeMessage = "Welcome to Biblioteca :)\n";
    private static String goodbyeMessage = "Thank you for using Biblioteca..\n";
    private static String menuHeading = "Please choose an action from the list below:";

    private static List<String> necessaryOptions = new ArrayList<String>();
    private static List<String> options = new ArrayList<String>();
    private static String fileName = "options.txt";
    private static String workingOptionFilePath = System.getProperty("user.dir") + "/options.txt";

    public static void openProgram() {
        necessaryOptions.clear();
        necessaryOptions.add("List Books\n");
        necessaryOptions.add("List Movies\n");
        options = necessaryOptions;

        System.out.println(welcomeMessage);
        showMenu();
    }

    public static void closeProgram() {
        System.out.println(goodbyeMessage);
    }

    public static void showMenu () {
        System.out.println(menuHeading);

        File tmpDir = new File(workingOptionFilePath);

        if (tmpDir.exists()) options = FileStream.readFromFile(fileName, necessaryOptions);
        else addOptionsToFile("");

        for (int i = 0; i < options.size(); i++) {
            System.out.println(i+1 + ") " + options.get(i));
        }

        System.out.print("\nEnter choice here: ");
    }

    public static void addOptionsToFile(String newOption) {
        options.add(newOption + "\n");
        FileStream.appendItemToFile(fileName, options);
        showMenu();
    }

    public static void removeOption(String optionToRemove) {
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i).toLowerCase().indexOf((optionToRemove).toLowerCase()) != -1) {
                options.remove(i);
                i = options.size();
            }
        }

        FileStream.removeItemsFromFile(fileName, options);
        showMenu();
    }

    public static void removeAllOptions() {
        options.clear();
        FileStream.removeItemsFromFile(fileName, options);
    }

    public static List<String> getOptions() {
        return options;
    }
}
