package com.twu.biblioteca;

import org.junit.Test;

import static org.junit.Assert.*;

public class BookTest {

    private Book emptyBook = new Book();
    private Book testBookAttribute = new Book("Lord of the Rings", "ME", 1994, false);
    private Book testBookDetail = new Book("Lord of the Rings 2           |Uncle               |2018 |Available");

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
        emptyBook.checkOutItem(5439212);
        assertTrue(emptyBook.getCheckOutStatus());
    }

    @Test
    public void testCheckOutBookForUnavailableBook() {
        assertFalse(testBookAttribute.getCheckOutStatus());
        testBookAttribute.checkOutItem(7506434);
        assertFalse(testBookAttribute.getCheckOutStatus());
    }

    @Test
    public void testCheckOutBookForAvailableBook() {
        testBookAttribute.recordBorrowerLibraryNumber(9834249);
        assertTrue(testBookDetail.getCheckOutStatus());

        testBookDetail.checkOutItem(6574543);

        assertFalse(testBookDetail.getCheckOutStatus());
        assertEquals(6574543, testBookDetail.getBorrowerLibraryNumber());
    }

    @Test
    public void testReturnBookForEmptyBook() {
        assertTrue(emptyBook.getCheckOutStatus());
        emptyBook.returnItem();
        assertTrue(emptyBook.getCheckOutStatus());
    }

    @Test
    public void testReturnBookForUnavailableBook() {
        testCheckOutBookForAvailableBook();

        assertFalse(testBookDetail.getCheckOutStatus());
        testBookDetail.returnItem();
        assertTrue(testBookDetail.getCheckOutStatus());
        assertEquals(0, testBookDetail.getBorrowerLibraryNumber());
    }

    @Test
    public void testReturnBookForAvailableBook() {
        assertTrue(testBookDetail.getCheckOutStatus());
        testBookDetail.returnItem();
        assertTrue(testBookDetail.getCheckOutStatus());
    }
}