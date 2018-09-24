package com.twu.biblioteca;

import java.io.*;
import java.util.*;

public class BookList implements MediaList <Book> {

    private String bookListHeader = "S/N  |" + String.format("%-30s", "Book Title") + "|" + String.format("%-20s", "Author") + "|Year\n";
    private String successfulCheckOutMessage = "Thank you! Enjoy the book.\n";
    private String unsuccessfulCheckOutMessage = "That book is not available.\n";
    private String successfulReturnMessage = "Thank you for returning the book.\n";
    private String unsuccessfulReturnMessage = "That is not a valid book to return.\n";
    private String emptyAvailableBookListMessage = "There are no available books right now, please try again later..\n";
    private String emptyUnavailableBookListMessage = "All books are returned ^^\n";
    private String noCheckOutItemsMessage = "There are currently no checked out books.\n";
    private String checkOutItemsMessage = "This is the list of books currently checked out:\n";

    private static String workingFilePath = System.getProperty("user.dir") + "/Book List.txt";

    private List<Book> bookList = new ArrayList<Book>();
    private List<Book> availableBookList = new ArrayList<Book>();
    private List<Book> unavailableBookList = new ArrayList<Book>();
    private List<String> allBookListDetail = new ArrayList<String>();
    private String listOfBooks;

    private String fileName = "Book List.txt";

    private int[] availableBooksArray;
    private int[] unavailableBooksArray;
    private int numberOfAvailableBooks;
    private int numberOfUnavailableBooks;

    public String listItems(List <Book> bookList) {
        listOfBooks = "";
        availableBooksArray = null;
        unavailableBooksArray = null;
        availableBookList.clear();
        unavailableBookList.clear();
        numberOfAvailableBooks = 0;
        numberOfUnavailableBooks = 0;

        if (bookList.size() == 0) {
            retrieveList();
            if (bookList.size() == 0) listOfBooks = emptyAvailableBookListMessage;
        }
        else {
            listOfBooks = listOfBooks + bookListHeader;
            availableBooksArray = new int[bookList.size()];
            unavailableBooksArray = new int[bookList.size()];

            for (int i = 0; i < bookList.size(); i++) {
                if (bookList.get(i).getCheckOutStatus()) {
                    availableBookList.add(bookList.get(i));
                    numberOfAvailableBooks++;
                    availableBooksArray[numberOfAvailableBooks - 1] = i;
                    listOfBooks = listOfBooks + detailWithSerialNumber(numberOfAvailableBooks, bookList.get(i).listDetail());

                }
                else {
                    unavailableBookList.add(bookList.get(i));
                    numberOfUnavailableBooks++;
                    unavailableBooksArray[numberOfUnavailableBooks - 1] = i;
                }
            }
            if (numberOfAvailableBooks == 0) listOfBooks = emptyAvailableBookListMessage;
        }
        return listOfBooks;
    }

    public String detailWithSerialNumber(int serial, String detail) {
        return String.format("%-5d", serial) + "|" + detail;
    }

    public String detailWithSerialNumberAndAccountNumber(int serial, String detail, String accountNumber) {
        return detailWithSerialNumber(serial, detail).trim() + " |" + accountNumber + "\n";
    }

    private void retrieveList() {
        bookList.clear();

        File tmpDir = new File(workingFilePath);

        if (tmpDir.exists()) allBookListDetail = FileStream.readFromFile(fileName, allBookListDetail);

        for (int i = 0; i < allBookListDetail.size(); i++) {
            Book newBook = new Book(allBookListDetail.get(i));
            bookList.add(newBook);
            if (bookList.get(bookList.size()-1).getCheckOutStatus()) numberOfAvailableBooks++;
        }

        if (!(allBookListDetail.size() == 0 && bookList.size() == 0)) listOfBooks = listItems(bookList);
    }

    public void addToList(Book newBook) {
        allBookListDetail.add(newBook.listAllDetail());
        FileStream.writeToFile(fileName, allBookListDetail);
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

        FileStream.removeItemsFromFile(fileName, allBookListDetail);
        retrieveList();
    }

    public void checkOutAnItem(int serial, String accountNumberOfBorrower) {
        if (serial < 1 || serial > numberOfAvailableBooks) {
            System.out.println(unsuccessfulCheckOutMessage);
        }
        else if (bookList.get(availableBooksArray[serial - 1]) != null &&
                bookList.get(availableBooksArray[serial - 1]).getCheckOutStatus()) {
            bookList.get(availableBooksArray[serial - 1]).checkOutItem(accountNumberOfBorrower);
            allBookListDetail.set(availableBooksArray[serial - 1], bookList.get(availableBooksArray[serial - 1]).listAllDetail());
            System.out.println(successfulCheckOutMessage);
        }
        else System.out.println(unsuccessfulCheckOutMessage);

        listItems(bookList);
        FileStream.writeToFile(fileName, allBookListDetail);
        retrieveList();
    }

    public String checkedOutItemList() {
        String checkedOutItems = "";

        if (numberOfUnavailableBooks == 0) System.out.print(noCheckOutItemsMessage);
        else {
            System.out.println(checkOutItemsMessage);
            checkedOutItems = bookListHeader.trim() + " |Borrowed by\n";

            for (int i = 0; i < numberOfUnavailableBooks; i++) {
                checkedOutItems = checkedOutItems +
                        detailWithSerialNumberAndAccountNumber(i + 1,
                        bookList.get(unavailableBooksArray[i]).listDetail(),
                        bookList.get(unavailableBooksArray[i]).getBorrowerAccountNumber());
            }
        }
        return checkedOutItems;
    }

    public void returnAnItem(int serial) {
        if (serial < 1 || serial > numberOfUnavailableBooks) {
            System.out.println(unsuccessfulReturnMessage);
        }
        else if (bookList.get(unavailableBooksArray[serial - 1]) != null &&
                !bookList.get(unavailableBooksArray[serial - 1]).getCheckOutStatus()) {
            bookList.get(unavailableBooksArray[serial - 1]).returnItem();
            allBookListDetail.set(unavailableBooksArray[serial - 1], bookList.get(unavailableBooksArray[serial - 1]).listAllDetail());
            System.out.println(successfulReturnMessage);
        }
        else System.out.println(unsuccessfulReturnMessage);

        listItems(bookList);
        FileStream.writeToFile(fileName, allBookListDetail);
        retrieveList();
    }

    public void printList(List<Book> list) {
        if (list.size() == 0) System.out.println(emptyUnavailableBookListMessage);
        else {
            System.out.print("\n" + bookListHeader);
            for (int i = 0; i < list.size(); i++) {
                System.out.print(detailWithSerialNumber(i + 1, list.get(i).listDetail()));
            }
            System.out.print("\n");
        }
    }

    public List<Book> getList() {
        return bookList;
    }

    public List<Book> getAvailableList() {
        return availableBookList;
    }

    public List<Book> getUnavailableList() {
        return unavailableBookList;
    }
}
