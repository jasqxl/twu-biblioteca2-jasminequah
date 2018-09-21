package com.twu.biblioteca;

import org.junit.*;
import java.io.*;
import static org.junit.Assert.*;

public class BookListTest {

    private static String bookListHeader = "S/N  |" + String.format("%-30s", "Book Title") + "|" + String.format("%-20s", "Author") + "|Year";
    private static String successfulCheckOutMessage = "Thank you! Enjoy the book.\n";
    private static String unsuccessfulCheckOutMessage = "That book is not available.\n";
    private static String successfulReturnMessage = "Thank you for returning the book.\n";
    private static String unsuccessfulReturnMessage = "That is not a valid book to return.\n";

    private Book testBook1 = new Book("Lord of the Rings", "ME", 1994, false);
    private Book testBook2 = new Book("Hang of the Rings 2           |Uncle               |2018 |available");
    private Book testBook3 = new Book("Book to handling bruh", "bot", 1967, true);
    private Book testBook4 = new Book("Lord of the Rings 2           |handphone           |2004 |Unavailable");
    private Book testBook5 = new Book("King an lah Rings", "kenny", 1994, false);
    private Book testBook6 = new Book("osfd of the Rings 2           |Uncle               |2018 |available");
    private Book testBook7 = new Book("Book sfv hanling bruh", "quah xue", 2012, true);
    private Book testBook8 = new Book("Lord sfgwef Rings 2           |dude                |2045 |Unavailable");

    private static BookList bookList = new BookList();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testAddOneBookToList() {
        bookList.removeAllItems();
        assertEquals(0, bookList.getList().size());

        bookList.addToList(testBook1);
        assertEquals(1, bookList.getList().size());
        assertEquals("Lord of the Rings", bookList.getList().get(0).getTitle());
        assertEquals("ME", bookList.getList().get(0).getCreator());
        assertEquals(1994, bookList.getList().get(0).getReleaseYear());

        bookList.removeAllItems();
    }

    @Test
    public void testAddMoreThanOneBookToList() {
        bookList.removeAllItems();
        assertEquals(0, bookList.getList().size());

        bookList.addToList(testBook1);
        bookList.addToList(testBook3);

        assertEquals(2, bookList.getList().size());
        assertEquals("Lord of the Rings", bookList.getList().get(0).getTitle());
        assertEquals("ME", bookList.getList().get(0).getCreator());
        assertEquals(1994, bookList.getList().get(0).getReleaseYear());
        assertEquals("Book to handling bruh", bookList.getList().get(1).getTitle());
        assertEquals("bot", bookList.getList().get(1).getCreator());
        assertEquals(1967, bookList.getList().get(1).getReleaseYear());

        bookList.removeAllItems();
    }

    @Test
    public void testRemoveOneBook() {
        bookList.removeAllItems();
        bookList.addToList(testBook1);
        bookList.addToList(testBook2);
        bookList.addToList(testBook3);
        bookList.addToList(testBook4);

        assertEquals(4, bookList.getList().size());

        bookList.removeItem(testBook2.getTitle(), testBook2.getCreator(), testBook2.getReleaseYear());

        assertEquals(3, bookList.getList().size());
        assertEquals("Book to handling bruh", bookList.getList().get(1).getTitle());
        assertEquals("bot", bookList.getList().get(1).getCreator());
        assertEquals(1967, bookList.getList().get(1).getReleaseYear());

        bookList.removeAllItems();
        assertEquals(0, bookList.getList().size());

        bookList.removeAllItems();
    }

    @Test
    public void testListBooks() {
        bookList.removeAllItems();
        bookList.addToList(testBook1);
        bookList.addToList(testBook2);
        bookList.addToList(testBook3);
        bookList.addToList(testBook4);

        assertEquals(4, bookList.getList().size());
        assertEquals(true, bookList.getList().get(2).getCheckOutStatus());

        assertEquals(bookListHeader + "\n" +
                //"Lord of the Rings             |ME                  |1994\n" +
                "1    |Hang of the Rings 2           |Uncle               |2018\n" +
                "2    |Book to handling bruh         |bot                 |1967\n"
                //"Lord of the Rings 2           |handphone           |2004\n"
                , bookList.listItems());

        bookList.removeAllItems();
    }

    @Test
    public void testCheckOutInvalidBook() {
        bookList.removeAllItems();
        bookList.addToList(testBook1);
        bookList.addToList(testBook2);
        bookList.addToList(testBook3);
        bookList.addToList(testBook4);
        bookList.addToList(testBook5);
        bookList.addToList(testBook6);
        bookList.addToList(testBook7);
        bookList.addToList(testBook8);

        bookList.checkOutAnItem(-3, 3974029);

        bookList.checkOutAnItem(0, 2376182);

        bookList.checkOutAnItem(5, 8328372);

        assertEquals(unsuccessfulCheckOutMessage + "\n" +
                unsuccessfulCheckOutMessage + "\n" +
                unsuccessfulCheckOutMessage + "\n"
                , outContent.toString());

        bookList.removeAllItems();
    }

    @Test
    public void testCheckOutValidBook() {
        bookList.removeAllItems();
        bookList.addToList(testBook1);
        bookList.addToList(testBook2);
        bookList.addToList(testBook3);
        bookList.addToList(testBook4);
        bookList.addToList(testBook5);
        bookList.addToList(testBook6);
        bookList.addToList(testBook7);
        bookList.addToList(testBook8);

        assertTrue(bookList.getList().get(1).getCheckOutStatus());
        bookList.checkOutAnItem(1, 4796328);
        assertFalse(bookList.getList().get(1).getCheckOutStatus());

        assertTrue(bookList.getList().get(6).getCheckOutStatus());
        bookList.checkOutAnItem(3, 2376282);
        assertFalse(bookList.getList().get(6).getCheckOutStatus());

        assertEquals(successfulCheckOutMessage + "\n" +
                successfulCheckOutMessage + "\n"
                , outContent.toString());

        bookList.removeAllItems();
    }

    @Test
    public void testReturnInvalidBook() {
        bookList.removeAllItems();
        bookList.addToList(testBook1);
        bookList.addToList(testBook2);
        bookList.addToList(testBook3);
        bookList.addToList(testBook4);
        bookList.addToList(testBook5);
        bookList.addToList(testBook8);

        bookList.returnAnItem(testBook6.getTitle(), testBook6.getCreator(), testBook6.getReleaseYear());

        assertTrue(bookList.getList().get(1).getCheckOutStatus());
        bookList.returnAnItem(testBook2.getTitle(), testBook2.getCreator(), testBook2.getReleaseYear());
        assertTrue(bookList.getList().get(1).getCheckOutStatus());

        assertEquals(unsuccessfulReturnMessage + "\n" +
                unsuccessfulReturnMessage + "\n"
                , outContent.toString());

        bookList.removeAllItems();
    }

    @Test
    public void testReturnValidBook() {
        bookList.removeAllItems();
        bookList.addToList(testBook1);
        bookList.addToList(testBook2);
        bookList.addToList(testBook3);
        bookList.addToList(testBook4);
        bookList.addToList(testBook5);
        bookList.addToList(testBook8);

        assertFalse(bookList.getList().get(3).getCheckOutStatus());
        bookList.returnAnItem(testBook4.getTitle(), testBook4.getCreator(), testBook4.getReleaseYear());
        assertTrue(bookList.getList().get(3).getCheckOutStatus());

        assertEquals(successfulReturnMessage + "\n", outContent.toString());

        bookList.removeAllItems();
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}