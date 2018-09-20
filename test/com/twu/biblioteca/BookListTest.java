package com.twu.biblioteca;

import org.junit.*;
import java.io.*;
import static org.junit.Assert.*;

public class BookListTest {

    private static String bookListHeader = "S/N  |" + String.format("%-30s", "Book Title") + "|" + String.format("%-20s", "Author") + "|Publish Year";
    private static String successfulCheckOutMessage = "Thank you! Enjoy the book.\n";
    private static String unsuccessfulCheckOutMessage = "That book is not available.\n";
    private static String successfulReturnMessage = "Thank you for returning the book.\n";
    private static String unsuccessfulReturnMessage = "That is not a valid book to return.\n";

    private Book testBook1 = new Book("Lord of the Rings", "ME", 1994, false);
    private Book testBook2 = new Book("Hang of the Rings 2           |Uncle               |2018    |available");
    private Book testBook3 = new Book("Book to handling bruh", "bot", 1967, true);
    private Book testBook4 = new Book("Lord of the Rings 2           |handphone           |2004    |Unavailable");
    private Book testBook5 = new Book("King an lah Rings", "kenny", 1994, false);
    private Book testBook6 = new Book("osfd of the Rings 2           |Uncle               |2018    |available");
    private Book testBook7 = new Book("Book sfv hanling bruh", "quah xue", 2012, true);
    private Book testBook8 = new Book("Lord sfgwef Rings 2           |dude                |2045    |Unavailable");

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testAddOneBookToList() {
        BookList.removeAllBooks();
        assertEquals(0, BookList.getBookList().size());

        BookList.addBookToList(testBook1);
        assertEquals(1, BookList.getBookList().size());
        assertEquals("Lord of the Rings", BookList.getBookList().get(0).getTitle());
        assertEquals("ME", BookList.getBookList().get(0).getCreator());
        assertEquals(1994, BookList.getBookList().get(0).getReleaseYear());

        BookList.removeAllBooks();
    }

    @Test
    public void testAddMoreThanOneBookToList() {
        BookList.removeAllBooks();
        assertEquals(0, BookList.getBookList().size());

        BookList.addBookToList(testBook1);
        BookList.addBookToList(testBook3);

        assertEquals(2, BookList.getBookList().size());
        assertEquals("Lord of the Rings", BookList.getBookList().get(0).getTitle());
        assertEquals("ME", BookList.getBookList().get(0).getCreator());
        assertEquals(1994, BookList.getBookList().get(0).getReleaseYear());
        assertEquals("Book to handling bruh", BookList.getBookList().get(1).getTitle());
        assertEquals("bot", BookList.getBookList().get(1).getCreator());
        assertEquals(1967, BookList.getBookList().get(1).getReleaseYear());

        BookList.removeAllBooks();
    }

    @Test
    public void testRemoveOneBook() {
        BookList.removeAllBooks();
        BookList.addBookToList(testBook1);
        BookList.addBookToList(testBook2);
        BookList.addBookToList(testBook3);
        BookList.addBookToList(testBook4);

        assertEquals(4, BookList.getBookList().size());

        BookList.removeBook(testBook2.getTitle(), testBook2.getCreator(), testBook2.getReleaseYear());

        assertEquals(3, BookList.getBookList().size());
        assertEquals("Book to handling bruh", BookList.getBookList().get(1).getTitle());
        assertEquals("bot", BookList.getBookList().get(1).getCreator());
        assertEquals(1967, BookList.getBookList().get(1).getReleaseYear());

        BookList.removeAllBooks();
        assertEquals(0, BookList.getBookList().size());

        BookList.removeAllBooks();
    }

    @Test
    public void testListBooks() {
        BookList.removeAllBooks();
        BookList.addBookToList(testBook1);
        BookList.addBookToList(testBook2);
        BookList.addBookToList(testBook3);
        BookList.addBookToList(testBook4);

        assertEquals(4, BookList.getBookList().size());
        assertEquals(true, BookList.getBookList().get(2).getCheckOutStatus());

        assertEquals(bookListHeader + "\n" +
                //"Lord of the Rings             |ME                  |1994\n" +
                "1    |Hang of the Rings 2           |Uncle               |2018\n" +
                "2    |Book to handling bruh         |bot                 |1967\n"
                //"Lord of the Rings 2           |handphone           |2004\n"
                , BookList.listBooks());

        BookList.removeAllBooks();
    }

    @Test
    public void testCheckOutInvalidBook() {
        BookList.removeAllBooks();
        BookList.addBookToList(testBook1);
        BookList.addBookToList(testBook2);
        BookList.addBookToList(testBook3);
        BookList.addBookToList(testBook4);
        BookList.addBookToList(testBook5);
        BookList.addBookToList(testBook6);
        BookList.addBookToList(testBook7);
        BookList.addBookToList(testBook8);

        BookList.checkOutABook(-3);

        BookList.checkOutABook(0);

        BookList.checkOutABook(5);

        assertEquals(unsuccessfulCheckOutMessage + "\n" +
                unsuccessfulCheckOutMessage + "\n" +
                unsuccessfulCheckOutMessage + "\n"
                , outContent.toString());

        BookList.removeAllBooks();
    }

    @Test
    public void testCheckOutValidBook() {
        BookList.removeAllBooks();
        BookList.addBookToList(testBook1);
        BookList.addBookToList(testBook2);
        BookList.addBookToList(testBook3);
        BookList.addBookToList(testBook4);
        BookList.addBookToList(testBook5);
        BookList.addBookToList(testBook6);
        BookList.addBookToList(testBook7);
        BookList.addBookToList(testBook8);

        assertTrue(BookList.getBookList().get(1).getCheckOutStatus());
        BookList.checkOutABook(1);
        assertFalse(BookList.getBookList().get(1).getCheckOutStatus());

        assertTrue(BookList.getBookList().get(6).getCheckOutStatus());
        BookList.checkOutABook(3);
        assertFalse(BookList.getBookList().get(6).getCheckOutStatus());

        assertEquals(successfulCheckOutMessage + "\n" +
                successfulCheckOutMessage + "\n"
                , outContent.toString());

        BookList.removeAllBooks();
    }

    @Test
    public void testReturnInvalidBook() {
        BookList.removeAllBooks();
        BookList.addBookToList(testBook1);
        BookList.addBookToList(testBook2);
        BookList.addBookToList(testBook3);
        BookList.addBookToList(testBook4);
        BookList.addBookToList(testBook5);
        BookList.addBookToList(testBook8);

        BookList.returnABook(testBook6.getTitle(), testBook6.getCreator(), testBook6.getReleaseYear());

        assertTrue(BookList.getBookList().get(1).getCheckOutStatus());
        BookList.returnABook(testBook2.getTitle(), testBook2.getCreator(), testBook2.getReleaseYear());
        assertTrue(BookList.getBookList().get(1).getCheckOutStatus());

        assertEquals(unsuccessfulReturnMessage + "\n" +
                unsuccessfulReturnMessage + "\n"
                , outContent.toString());

        BookList.removeAllBooks();
    }

    @Test
    public void testReturnValidBook() {
        BookList.removeAllBooks();
        BookList.addBookToList(testBook1);
        BookList.addBookToList(testBook2);
        BookList.addBookToList(testBook3);
        BookList.addBookToList(testBook4);
        BookList.addBookToList(testBook5);
        BookList.addBookToList(testBook8);

        assertFalse(BookList.getBookList().get(3).getCheckOutStatus());
        BookList.returnABook(testBook4.getTitle(), testBook4.getCreator(), testBook4.getReleaseYear());
        assertTrue(BookList.getBookList().get(3).getCheckOutStatus());

        assertEquals(successfulReturnMessage + "\n", outContent.toString());

        BookList.removeAllBooks();
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}