package com.twu.biblioteca;

import java.io.*;
import java.util.*;

public class FileStream <T extends Media> {

    private static String line;

    public static List<String> readFromFile(String fileName, List<String> listItem) {
        List<String> listOfItems = new ArrayList<String>();

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                if (line.length() != 0) listOfItems.add(line);
            }

            if (listOfItems.size() < listItem.size()) {
                appendItemToFile(fileName, listItem);
                listOfItems = readFromFile(fileName, listItem);
            }

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
        return listOfItems;
    }

    public static void appendItemToFile(String fileName, List<String> newItem) {
        try (FileWriter fileWriter = new FileWriter(fileName, false);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {
            for (int i = 0; i < newItem.size(); i++) {
                if (newItem.get(i).length() != 0) printWriter.println(newItem.get(i));
            }
        }
        catch(IOException ex1) {
            System.out.println("Error reading file '" + fileName + "'");
        }
    }

    public static void removeItemsFromFile (String fileName, List<String> remainingItems) {
        try (FileWriter fileWriter = new FileWriter(fileName, false);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {

            for (int i = 0; i < remainingItems.size(); i++) {
                printWriter.println(remainingItems.get(i));
            }
        }
        catch(IOException ex1) {
            System.out.println("Error reading file '" + fileName + "'");
        }
    }
}
