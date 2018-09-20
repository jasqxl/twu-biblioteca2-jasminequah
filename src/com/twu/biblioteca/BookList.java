package com.twu.biblioteca;

import java.io.*;
import java.util.*;
public class BookList {

    private static String bookListHeader = "S/N  |" + String.format("%-30s", "Book Title") + "|" + String.format("%-20s", "Author") + "|Publish Year";
    private static String successfulCheckOutMessage = "Thank you! Enjoy the book.\n";
    private static String unsuccessfulCheckOutMessage = "That book is not available.\n";
    private static String successfulReturnMessage = "Thank you for returning the book.\n";
    private static String unsuccessfulReturnMessage = "That is not a valid book to return.\n";
    private static String emptyBookListMessage = "There are no available books right now, please try again later..\n";

    private static List<Book> bookList = new ArrayList<Book>();

    private static String fileName = "Book List.txt";
    private static String line = null;
    private static int[] bookSerialNumberArray;
    private static int numberOfAvailableBooks;
    private static String listOfBooks;

    private static void retrieveBookList () {
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
        listOfBooks = listBooks();
    }

    public static void addBookToFile(Book newBook) {
        try (FileWriter fileWriter = new FileWriter(fileName, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {
            printWriter.println(newBook.listAllBookDetail());
        }
        catch(IOException ex1) {
            System.out.println("Error reading file '" + fileName + "'");
        }
        retrieveBookList();
    }

    public static void removeBook(String title, String author, int publishYear) {
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getTitle().toLowerCase().indexOf(title.toLowerCase()) != -1 &&
                    bookList.get(i).getAuthor().toLowerCase().indexOf(author.toLowerCase()) != -1 &&
                    bookList.get(i).getPublishYear() == publishYear) {
                bookList.remove(i);
                i = bookList.size();
            }
        }

        try (FileWriter fileWriter = new FileWriter(fileName, false);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {

            for (int i = 0; i < bookList.size(); i++) {
                printWriter.println(bookList.get(i).listAllBookDetail());
            }
        }
        catch(IOException ex1) {
            System.out.println("Error reading file '" + fileName + "'");
        }
        retrieveBookList();
    }

    public static void removeAllBooks() {
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

    public static String listBooks() {
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
                    listOfBooks = listOfBooks + String.format("%-5d", bookSerialNumber) + "|" + bookList.get(i).listBookDetail() + "\n";
                    bookSerialNumber++;
                }
            }
        }

        return listOfBooks;
    }

    public static void checkOutABook(int serial) {
        bookSerialNumberArray = null;
        listOfBooks = listBooks();

        if (serial < 1 || serial > numberOfAvailableBooks) {
            System.out.println(unsuccessfulCheckOutMessage);
        }
        else if (bookList.get(bookSerialNumberArray[serial - 1]) != null &&
                bookList.get(bookSerialNumberArray[serial - 1]).getCheckOutStatus()) {
            bookList.get(bookSerialNumberArray[serial - 1]).checkOutBook();
            System.out.println(successfulCheckOutMessage);
        }
        else {
            System.out.println(unsuccessfulCheckOutMessage);
        }

        bookSerialNumberArray = null;
        listOfBooks = listBooks();
    }

    public static void returnABook(String title, String author, int publishYear) {
        Boolean isReturned = false;

        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getTitle().toLowerCase().indexOf(title.toLowerCase()) != -1 &&
                    bookList.get(i).getAuthor().toLowerCase().indexOf(author.toLowerCase()) != -1 &&
                    bookList.get(i).getPublishYear() == publishYear &&
                    bookList.get(i).getCheckOutStatus() == false) {
                bookList.get(i).returnBook();
                isReturned = true;
                System.out.println(successfulReturnMessage);
                i = bookList.size();
            }
        }

        if (!isReturned) {
            System.out.println(unsuccessfulReturnMessage);
        }

        bookSerialNumberArray = null;
        listOfBooks = listBooks();
    }

    public static List<Book> getBookList() {
        return bookList;
    }
}
