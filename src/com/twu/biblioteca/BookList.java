package com.twu.biblioteca;

import java.io.*;
import java.util.*;
public class BookList implements MediaList <Book> {

    private String bookListHeader = "S/N  |" + String.format("%-30s", "Book Title") + "|" + String.format("%-20s", "Author") + "|Year";
    private String successfulCheckOutMessage = "Thank you! Enjoy the book.\n";
    private String unsuccessfulCheckOutMessage = "That book is not available.\n";
    private String successfulReturnMessage = "Thank you for returning the book.\n";
    private String unsuccessfulReturnMessage = "That is not a valid book to return.\n";
    private String emptyBookListMessage = "There are no available books right now, please try again later..\n";

    private List<Book> bookList = new ArrayList<Book>();

    private String fileName = "Book List.txt";
    private String line = null;
    private int[] bookSerialNumberArray;
    private int numberOfAvailableBooks;
    private String listOfBooks;

    public String listItems() {
        listOfBooks = "";

        if (bookList.size() == 0) {
            listOfBooks = emptyBookListMessage;
        }
        else {
            listOfBooks = listOfBooks + bookListHeader + "\n";
            int bookSerialNumber = 1;
            bookSerialNumberArray = new int[bookList.size()];
            numberOfAvailableBooks = 0;

            for (int i = 0; i < bookList.size(); i++) {
                if (bookList.get(i).getCheckOutStatus()) {
                    numberOfAvailableBooks++;
                    bookSerialNumberArray[bookSerialNumber - 1] = i;
                    listOfBooks = listOfBooks + String.format("%-5d", bookSerialNumber) + "|" + bookList.get(i).listDetail() + "\n";
                    bookSerialNumber++;
                }
            }
        }

        return listOfBooks;
    }

    public void retrieveList () {
        bookList.clear();
        numberOfAvailableBooks = 0;

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                Book newBook = new Book(line);
                bookList.add(newBook);
                if (bookList.get(bookList.size()-1).getCheckOutStatus()) numberOfAvailableBooks++;
            }
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }

        bookSerialNumberArray = null;
        listOfBooks = listItems();
    }

    public void addToList(Book newBook) {
        try (FileWriter fileWriter = new FileWriter(fileName, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {
            printWriter.println(newBook.listAllDetail());
        }
        catch(IOException ex1) {
            System.out.println("Error reading file '" + fileName + "'");
        }
        retrieveList();
    }

    public void removeItem(String title, String creator, int publishYear) {
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getTitle().toLowerCase().indexOf(title.toLowerCase()) != -1 &&
                    bookList.get(i).getCreator().toLowerCase().indexOf(creator.toLowerCase()) != -1 &&
                    bookList.get(i).getReleaseYear() == publishYear) {
                bookList.remove(i);
                i = bookList.size();
            }
        }

        try (FileWriter fileWriter = new FileWriter(fileName, false);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {

            for (int i = 0; i < bookList.size(); i++) {
                printWriter.println(bookList.get(i).listAllDetail());
            }
        }
        catch(IOException ex1) {
            System.out.println("Error reading file '" + fileName + "'");
        }
        retrieveList();
    }

    public void removeAllItems() {
        bookList.clear();
        bookSerialNumberArray = null;
        listOfBooks = null;
        numberOfAvailableBooks = 0;

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
        bookSerialNumberArray = null;
        listOfBooks = listItems();

        if (serial < 1 || serial > numberOfAvailableBooks) {
            System.out.println(unsuccessfulCheckOutMessage);
        }
        else if (bookList.get(bookSerialNumberArray[serial - 1]) != null &&
                bookList.get(bookSerialNumberArray[serial - 1]).getCheckOutStatus()) {
            bookList.get(bookSerialNumberArray[serial - 1]).checkOutItem();
            System.out.println(successfulCheckOutMessage);
        }
        else {
            System.out.println(unsuccessfulCheckOutMessage);
        }

        bookSerialNumberArray = null;
        listOfBooks = listItems();
    }

    public void returnAnItem(String title, String creator, int publishYear) {
        Boolean isReturned = false;

        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getTitle().toLowerCase().indexOf(title.toLowerCase()) != -1 &&
                    bookList.get(i).getCreator().toLowerCase().indexOf(creator.toLowerCase()) != -1 &&
                    bookList.get(i).getReleaseYear() == publishYear &&
                    bookList.get(i).getCheckOutStatus() == false) {
                bookList.get(i).returnItem();
                isReturned = true;
                System.out.println(successfulReturnMessage);
                i = bookList.size();
            }
        }

        if (!isReturned) {
            System.out.println(unsuccessfulReturnMessage);
        }

        bookSerialNumberArray = null;
        listOfBooks = listItems();
    }

    public List<Book> getList() {
        return bookList;
    }
}
