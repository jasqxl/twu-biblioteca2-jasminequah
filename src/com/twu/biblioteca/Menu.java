package com.twu.biblioteca;

import java.io.*;
import java.util.*;

public class Menu {

    private static String welcomeMessage = "Welcome to Biblioteca :)\n";
    private static String goodbyeMessage = "Thank you for using Biblioteca..\n";
    private static String menuHeading = "Please choose an action from the list below:";

    private static List<String> Options = new ArrayList<String>();
    private static String fileName = "Options.txt";
    private static String line = null;

    //private static String workingOptionFilePath = System.getProperty("user.dir") + "/Options.txt";

    public static void openProgram() {
        System.out.println(welcomeMessage);
        showMenu();
    }

    public static void closeProgram() {
        System.out.println(goodbyeMessage);
    }

    public static void showMenu () {
        Options.clear();

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            System.out.println(menuHeading);

            while((line = bufferedReader.readLine()) != null) {
                Options.add(line);
                System.out.println(Options.size() + ") " + Options.get(Options.size()-1));
            }
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            writeToOptionsFile("List Books");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }

    }

    private static void writeToOptionsFile(String newOption) {
        try (FileWriter fileWriter = new FileWriter(fileName, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {
            printWriter.println(newOption);
        }
        catch(IOException ex1) {
            System.out.println("Error reading file '" + fileName + "'");
        }
        showMenu();
    }

    public static void removeOptions(String optionToRemove) {

        for (int i = 0; i < Options.size(); i++) {
            if (Options.get(i).toLowerCase().indexOf(optionToRemove.toLowerCase()) != -1) {
                Options.remove(i);
                i = Options.size();
            }
        }

        try (FileWriter fileWriter = new FileWriter(fileName, false);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {

            for (int i = 0; i < Options.size(); i++) {
                printWriter.println(Options.get(i));
            }
        }
        catch(IOException ex1) {
            System.out.println("Error reading file '" + fileName + "'");
        }
        showMenu();
    }

    public static void removeAllOptions() {
        Options.clear();
        Options.add("List Books");

        try (FileWriter fileWriter = new FileWriter(fileName, false);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {
            printWriter.println("List Books");
        }
        catch(IOException ex1) {
            System.out.println("Error reading file '" + fileName + "'");
        }
    }

    public static void addOption(String newOptionToAdd) {
        writeToOptionsFile(newOptionToAdd);
    }

    public static List<String> getOptions() {
        return Options;
    }
}
