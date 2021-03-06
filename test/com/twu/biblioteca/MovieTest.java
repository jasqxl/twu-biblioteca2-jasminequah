package com.twu.biblioteca;

import org.junit.Test;

import static org.junit.Assert.*;

public class MovieTest {

    private Movie emptyMovie = new Movie();
    private Movie testMovieAttribute = new Movie("Lord of the Rings", "quah", 1994, false, 4.5);
    private Movie testMovieDetail = new Movie("Lord of the Rings 2           |Uncle               |2018 |6      |Available");

    @Test
    public void testGetTitle() {
        assertEquals(null, emptyMovie.getTitle());
        assertEquals("Lord of the Rings", testMovieAttribute.getTitle());
        assertEquals("Lord of the Rings 2", testMovieDetail.getTitle());
    }

    @Test
    public void testGetDirector() {
        assertEquals(null, emptyMovie.getCreator());
        assertEquals("quah", testMovieAttribute.getCreator());
        assertEquals("Uncle", testMovieDetail.getCreator());
    }

    @Test
    public void testGetReleaseYear() {
        assertEquals(0, emptyMovie.getReleaseYear());
        assertEquals(1994, testMovieAttribute.getReleaseYear());
        assertEquals(2018, testMovieDetail.getReleaseYear());
    }

    @Test
    public void testListMovieDetail() {
        assertEquals(null, emptyMovie.listDetail());
        assertEquals("Lord of the Rings             |quah                |1994 |4.5\n", testMovieAttribute.listDetail());
        assertEquals("Lord of the Rings 2           |Uncle               |2018 |6.0\n", testMovieDetail.listDetail());
    }

    @Test
    public void testGetCheckOutStatus() {
        assertTrue(emptyMovie.getCheckOutStatus());
        assertFalse(testMovieAttribute.getCheckOutStatus());
        assertTrue(testMovieDetail.getCheckOutStatus());
    }

    @Test
    public void testCheckOutMovieForEmptyMovie() {
        assertTrue(emptyMovie.getCheckOutStatus());
        emptyMovie.checkOutItem("433-6345");
        assertTrue(emptyMovie.getCheckOutStatus());
    }

    @Test
    public void testCheckOutMovieForUnavailableMovie() {
        assertFalse(testMovieAttribute.getCheckOutStatus());
        testMovieAttribute.checkOutItem("345-4534");
        assertFalse(testMovieAttribute.getCheckOutStatus());
    }

    @Test
    public void testCheckOutMovieForAvailableMovie() {
        assertTrue(testMovieDetail.getCheckOutStatus());
        testMovieDetail.checkOutItem("er4-43w3");
        assertFalse(testMovieDetail.getCheckOutStatus());
    }

    @Test
    public void testReturnMovieForEmptyMovie() {
        assertTrue(emptyMovie.getCheckOutStatus());
        emptyMovie.returnItem();
        assertTrue(emptyMovie.getCheckOutStatus());
    }

    @Test
    public void testReturnMovieForUnavailableMovie() {
        assertFalse(testMovieAttribute.getCheckOutStatus());
        testMovieAttribute.returnItem();
        assertTrue(testMovieAttribute.getCheckOutStatus());
    }

    @Test
    public void testReturnMovieForAvailableMovie() {
        assertTrue(testMovieDetail.getCheckOutStatus());
        testMovieDetail.returnItem();
        assertTrue(testMovieDetail.getCheckOutStatus());
    }
}