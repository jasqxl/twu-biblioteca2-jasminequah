package com.twu.biblioteca;

import org.junit.*;
import java.io.*;
import static org.junit.Assert.*;

public class BookListTest {

    private static String bookListHeader = "S/N  |" + String.format("%-30s", "Book Title") + "|" + String.format("%-20s", "Author") + "|Year\n";
    private static String successfulCheckOutMessage = "Thank you! Enjoy the book.\n";
    private static String unsuccessfulCheckOutMessage = "That book is not available.\n";
    private static String successfulReturnMessage = "Thank you for returning the book.\n";
    private static String unsuccessfulReturnMessage = "That is not a valid book to return.\n";
    private String noCheckOutItemsMessage = "There are currently no checked out books.\n";
    private String checkOutItemsMessage = "This is the list of books currently checked out:\n";

    private Book testBook1 = new Book("Lord of the Rings", "ME", 1994, "459-JGBU");
    private Book testBook2 = new Book("Hang of the Rings 2           |Uncle               |2018 |");
    private Book testBook3 = new Book("Book to handling bruh", "bot", 1967, "");
    private Book testBook4 = new Book("Lord of the Rings 2           |handphone           |2004 |32h-23oi");
    private Book testBook5 = new Book("King an lah Rings", "kenny", 1994, "329-439j");
    private Book testBook6 = new Book("osfd of the Rings 2           |Uncle               |2018 |");
    private Book testBook7 = new Book("Book sfv hanling bruh", "quah xue", 2012, "");
    private Book testBook8 = new Book("Lord sfgwef Rings 2           |dude                |2045 |32h-23oi");

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
    public void testNoCheckOutItemsList() {
        bookList.removeAllItems();
        assertEquals(0, bookList.getList().size());

        bookList.addToList(testBook2);
        bookList.addToList(testBook3);
        bookList.addToList(testBook6);
        bookList.addToList(testBook7);

        assertEquals(4, bookList.getList().size());
        assertEquals("Hang of the Rings 2", bookList.getList().get(0).getTitle());
        assertEquals("Uncle", bookList.getList().get(0).getCreator());
        assertEquals(2018, bookList.getList().get(0).getReleaseYear());
        assertEquals("Book to handling bruh", bookList.getList().get(1).getTitle());
        assertEquals("bot", bookList.getList().get(1).getCreator());
        assertEquals(1967, bookList.getList().get(1).getReleaseYear());
        assertEquals("Book sfv hanling bruh", bookList.getList().get(3).getTitle());
        assertEquals("quah xue", bookList.getList().get(3).getCreator());
        assertEquals(2012, bookList.getList().get(3).getReleaseYear());

        assertEquals("", bookList.checkedOutItemList());
        assertEquals(noCheckOutItemsMessage, outContent.toString());

        bookList.removeAllItems();
    }

    @Test
    public void testCheckOutItemsList() {
        bookList.removeAllItems();
        assertEquals(0, bookList.getList().size());

        bookList.addToList(testBook1);
        bookList.addToList(testBook3);
        bookList.addToList(testBook2);
        bookList.addToList(testBook4);

        assertEquals(4, bookList.getList().size());
        assertEquals("Lord of the Rings", bookList.getList().get(0).getTitle());
        assertEquals("ME", bookList.getList().get(0).getCreator());
        assertEquals(1994, bookList.getList().get(0).getReleaseYear());
        assertEquals("Book to handling bruh", bookList.getList().get(1).getTitle());
        assertEquals("bot", bookList.getList().get(1).getCreator());
        assertEquals(1967, bookList.getList().get(1).getReleaseYear());
        assertEquals("Lord of the Rings 2", bookList.getList().get(3).getTitle());
        assertEquals("handphone", bookList.getList().get(3).getCreator());
        assertEquals(2004, bookList.getList().get(3).getReleaseYear());

        System.out.println(bookList.checkedOutItemList());

        assertEquals(checkOutItemsMessage + "\n" + bookListHeader.trim() + " |Borrowed by\n" +
                bookList.detailWithSerialNumberAndAccountNumber(1, testBook1.listDetail(), testBook1.getBorrowerAccountNumber()) +
                bookList.detailWithSerialNumberAndAccountNumber(2, testBook4.listDetail(), testBook4.getBorrowerAccountNumber()) + "\n"
                , outContent.toString());

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

        assertEquals(bookListHeader +
                //"Lord of the Rings             |ME                  |1994\n" +
                "1    |Hang of the Rings 2           |Uncle               |2018\n" +
                "2    |Book to handling bruh         |bot                 |1967\n"
                //"Lord of the Rings 2           |handphone           |2004\n"
                , bookList.listItems(bookList.getList()));

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

        bookList.checkOutAnItem(-3, "397-4029");

        bookList.checkOutAnItem(0, "237-6182");

        bookList.checkOutAnItem(5, "832-8372");

        assertEquals(unsuccessfulCheckOutMessage + "\n" +
                unsuccessfulCheckOutMessage + "\n" +
                unsuccessfulCheckOutMessage + "\n"
                , outContent.toString());

        //bookList.removeAllItems();
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
        bookList.checkOutAnItem(1, "479-6328");
        assertFalse(bookList.getList().get(1).getCheckOutStatus());

        assertTrue(bookList.getList().get(6).getCheckOutStatus());
        bookList.checkOutAnItem(3, "237-6282");
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

        assertEquals(4,bookList.getUnavailableList().size());
        bookList.returnAnItem(5);
        bookList.returnAnItem(0);
        assertEquals(4,bookList.getUnavailableList().size());

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

        assertEquals(4, bookList.getUnavailableList().size());
        bookList.returnAnItem(4);
        assertEquals(3, bookList.getUnavailableList().size());
        assertEquals(successfulReturnMessage + "\n", outContent.toString());

        bookList.removeAllItems();
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}