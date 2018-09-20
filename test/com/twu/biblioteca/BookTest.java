package com.twu.biblioteca;

import org.junit.Test;

import static org.junit.Assert.*;

public class BookTest {
    private static String successfulCheckOutMessage = "Thank you! Enjoy the book.\n";
    private static String unsuccessfulCheckOutMessage = "That book is not available.\n";
    private static String successfulReturnMessage = "Thank you for returning the book.\n";
    private static String unsuccessfulReturnMessage = "That is not a valid book to return.\n";

    private Book emptyBook = new Book();
    private Book testBookAttribute = new Book("Lord of the Rings", "ME", 1994, false);
    private Book testBookDetail = new Book("Lord of the Rings 2           |Uncle               |2018    |Available");

    @Test
    public void testGetTitle() {
        assertEquals(null, emptyBook.getTitle());
        assertEquals("Lord of the Rings", testBookAttribute.getTitle());
        assertEquals("Lord of the Rings 2", testBookDetail.getTitle());
    }

    @Test
    public void testGetAuthor() {
        assertEquals(null, emptyBook.getCreator());
        assertEquals("ME", testBookAttribute.getCreator());
        assertEquals("Uncle", testBookDetail.getCreator());
    }

    @Test
    public void testGetPublishYear() {
        assertEquals(0, emptyBook.getReleaseYear());
        assertEquals(1994, testBookAttribute.getReleaseYear());
        assertEquals(2018, testBookDetail.getReleaseYear());
    }

    @Test
    public void testListBookDetail() {
        assertEquals(null, emptyBook.listDetail());
        assertEquals("Lord of the Rings             |ME                  |1994", testBookAttribute.listDetail());
        assertEquals("Lord of the Rings 2           |Uncle               |2018", testBookDetail.listDetail());
    }

    @Test
    public void testGetCheckOutStatus() {
        assertTrue(emptyBook.getCheckOutStatus());
        assertFalse(testBookAttribute.getCheckOutStatus());
        assertTrue(testBookDetail.getCheckOutStatus());
    }

    @Test
    public void testCheckOutBookForEmptyBook() {
        assertTrue(emptyBook.getCheckOutStatus());
        emptyBook.checkOutItem();
        assertTrue(emptyBook.getCheckOutStatus());
    }

    @Test
    public void testCheckOutBookForUnavailableBook() {
        assertFalse(testBookAttribute.getCheckOutStatus());
        testBookAttribute.checkOutItem();
        assertFalse(testBookAttribute.getCheckOutStatus());
    }

    @Test
    public void testCheckOutBookForAvailableBook() {
        assertTrue(testBookDetail.getCheckOutStatus());
        testBookDetail.checkOutItem();
        assertFalse(testBookDetail.getCheckOutStatus());
    }

    @Test
    public void testReturnBookForEmptyBook() {
        assertTrue(emptyBook.getCheckOutStatus());
        emptyBook.returnItem();
        assertTrue(emptyBook.getCheckOutStatus());
    }

    @Test
    public void testReturnBookForUnavailableBook() {
        assertFalse(testBookAttribute.getCheckOutStatus());
        testBookAttribute.returnItem();
        assertTrue(testBookAttribute.getCheckOutStatus());
    }

    @Test
    public void testReturnBookForAvailableBook() {
        assertTrue(testBookDetail.getCheckOutStatus());
        testBookDetail.returnItem();
        assertTrue(testBookDetail.getCheckOutStatus());
    }
}