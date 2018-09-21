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

    private static String workingOptionFilePath = System.getProperty("user.dir") + "/Book List.txt";

    private List<Book> bookList = new ArrayList<Book>();
    private List<String> allBookListDetail = new ArrayList<String>();

    private String fileName = "Book List.txt";
    private int[] bookSerialNumberArray;
    private int numberOfAvailableBooks;
    private String listOfBooks;

    public String listItems() {
        listOfBooks = "";
        numberOfAvailableBooks = 0;

        if (bookList.size() == 0) {
            listOfBooks = emptyBookListMessage;
        }
        else {
            listOfBooks = listOfBooks + bookListHeader + "\n";
            int bookSerialNumber = 1;
            bookSerialNumberArray = new int[bookList.size()];

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

        File tmpDir = new File(workingOptionFilePath);

        if (tmpDir.exists()) allBookListDetail = FileStream.readFromFile(fileName, allBookListDetail);

        for (int i = 0; i < allBookListDetail.size(); i++) {
            Book newBook = new Book(allBookListDetail.get(i));
            bookList.add(newBook);
            if (bookList.get(bookList.size()-1).getCheckOutStatus()) numberOfAvailableBooks++;
        }

        bookSerialNumberArray = null;
        listOfBooks = listItems();
    }

    public void addToList(Book newBook) {
        allBookListDetail.add(newBook.listAllDetail());
        FileStream.appendItemToFile(fileName, allBookListDetail);
        retrieveList();
    }

    public void removeItem(String title, String creator, int publishYear) {
        for (int i = 0; i < allBookListDetail.size(); i++) {
            if (bookList.get(i).getTitle().toLowerCase().indexOf(title.toLowerCase()) != -1 &&
                    bookList.get(i).getCreator().toLowerCase().indexOf(creator.toLowerCase()) != -1 &&
                    bookList.get(i).getReleaseYear() == publishYear) {
                allBookListDetail.remove(i);
                i = allBookListDetail.size();
            }
        }

        FileStream.removeItemsFromFile(fileName, allBookListDetail);
        retrieveList();
    }

    public void removeAllItems() {
        bookList.clear();
        allBookListDetail.clear();
        bookSerialNumberArray = null;
        listOfBooks = null;
        numberOfAvailableBooks = 0;

        FileStream.removeItemsFromFile(fileName, allBookListDetail);
    }

    public void checkOutAnItem(int serial, int libraryNumberOfBorrower) {
        bookSerialNumberArray = null;
        listOfBooks = listItems();

        if (serial < 1 || serial > numberOfAvailableBooks) {
            System.out.println(unsuccessfulCheckOutMessage);
        }
        else if (bookList.get(bookSerialNumberArray[serial - 1]) != null &&
                bookList.get(bookSerialNumberArray[serial - 1]).getCheckOutStatus()) {
            bookList.get(bookSerialNumberArray[serial - 1]).checkOutItem(libraryNumberOfBorrower);
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
