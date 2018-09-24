package com.twu.biblioteca;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class MovieListTest {

    private static String movieListHeader = "S/N  |" + String.format("%-30s", "Movie Title") + "|" + String.format("%-20s", "Director") + "|Year |Rating\n";
    private static String successfulCheckOutMessage = "Thank you! Enjoy the movie.\n";
    private static String unsuccessfulCheckOutMessage = "That movie is not available.\n";
    private static String successfulReturnMessage = "Thank you for returning the movie.\n";
    private static String unsuccessfulReturnMessage = "That is not a valid movie to return.\n";

    private Movie testMovie1 = new Movie("Lord of the Rings", "ME", 1994, false,7.5);
    private Movie testMovie2 = new Movie("Hang of the Rings 2           |Uncle               |2018 |10     |Available");
    private Movie testMovie3 = new Movie("Movie to handling bruh", "bot", 1967, true,4.4);
    private Movie testMovie4 = new Movie("Lord of the Rings 2           |handphone           |2004 |5.23   |unavailable");
    private Movie testMovie5 = new Movie("King an lah Rings", "kenny", 1994, false, 9.5);
    private Movie testMovie6 = new Movie("osfd of the Rings 2           |Uncle               |2018 |6.8    |available");
    private Movie testMovie7 = new Movie("Movie sfv hanling bruh", "quah xue", 2012, true, 7.0);
    private Movie testMovie8 = new Movie("Lord sfgwef Rings 2           |dude                |2045 |8      |Unavailable");

    private static MovieList movieList = new MovieList();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testAddOneMovieToList() {
        movieList.removeAllItems();
        assertEquals(0, movieList.getList().size());

        movieList.addToList(testMovie1);
        assertEquals(1, movieList.getList().size());
        assertEquals("Lord of the Rings", movieList.getList().get(0).getTitle());
        assertEquals("ME", movieList.getList().get(0).getCreator());
        assertEquals(1994, movieList.getList().get(0).getReleaseYear());

        movieList.removeAllItems();
    }

    @Test
    public void testAddMoreThanOneMovieToList() {
        movieList.removeAllItems();
        assertEquals(0, movieList.getList().size());

        movieList.addToList(testMovie1);
        movieList.addToList(testMovie3);

        assertEquals(2, movieList.getList().size());
        assertEquals("Lord of the Rings", movieList.getList().get(0).getTitle());
        assertEquals("ME", movieList.getList().get(0).getCreator());
        assertEquals(1994, movieList.getList().get(0).getReleaseYear());
        assertEquals("Movie to handling bruh", movieList.getList().get(1).getTitle());
        assertEquals("bot", movieList.getList().get(1).getCreator());
        assertEquals(1967, movieList.getList().get(1).getReleaseYear());

        movieList.removeAllItems();
    }

    @Test
    public void testRemoveOneMovie() {
        movieList.removeAllItems();
        movieList.addToList(testMovie1);
        movieList.addToList(testMovie2);
        movieList.addToList(testMovie3);
        movieList.addToList(testMovie4);

        assertEquals(4, movieList.getList().size());

        movieList.removeItem(testMovie2.getTitle(), testMovie2.getCreator(), testMovie2.getReleaseYear());

        assertEquals(3, movieList.getList().size());
        assertEquals("Movie to handling bruh", movieList.getList().get(1).getTitle());
        assertEquals("bot", movieList.getList().get(1).getCreator());
        assertEquals(1967, movieList.getList().get(1).getReleaseYear());

        movieList.removeAllItems();
        assertEquals(0, movieList.getList().size());

        movieList.removeAllItems();
    }

    @Test
    public void testListMovies() {
        movieList.removeAllItems();

        movieList.addToList(testMovie1);
        movieList.addToList(testMovie2);
        movieList.addToList(testMovie3);
        movieList.addToList(testMovie4);

        assertEquals(4, movieList.getList().size());
        assertEquals(true, movieList.getList().get(2).getCheckOutStatus());
        assertEquals(false, movieList.getList().get(3).getCheckOutStatus());

        assertEquals(movieListHeader +
                //"Lord of the Rings             |ME                  |1994 |7.5\n" +
                "1    |Hang of the Rings 2           |Uncle               |2018 |10.0\n" +
                "2    |Movie to handling bruh        |bot                 |1967 |4.4\n"
                //"Lord of the Rings 2           |handphone           |2004 |5.23\n"
                , movieList.listItems(movieList.getList()));

        movieList.removeAllItems();
    }

    @Test
    public void testCheckOutInvalidMovie() {
        movieList.removeAllItems();
        movieList.addToList(testMovie1);
        movieList.addToList(testMovie2);
        movieList.addToList(testMovie3);
        movieList.addToList(testMovie4);
        movieList.addToList(testMovie5);
        movieList.addToList(testMovie6);
        movieList.addToList(testMovie7);
        movieList.addToList(testMovie8);

        movieList.checkOutAnItem(-3, "235-2353");

        movieList.checkOutAnItem(0, "567-5675");

        movieList.checkOutAnItem(5, "43t-54tf");

        assertEquals(unsuccessfulCheckOutMessage + "\n" +
                unsuccessfulCheckOutMessage + "\n" +
                unsuccessfulCheckOutMessage + "\n"
                , outContent.toString());

        //movieList.removeAllItems();
    }

    @Test
    public void testCheckOutValidMovie() {
        movieList.removeAllItems();
        movieList.addToList(testMovie1);
        movieList.addToList(testMovie2);
        movieList.addToList(testMovie3);
        movieList.addToList(testMovie4);
        movieList.addToList(testMovie5);
        movieList.addToList(testMovie6);
        movieList.addToList(testMovie7);
        movieList.addToList(testMovie8);

        assertTrue(movieList.getList().get(1).getCheckOutStatus());
        movieList.checkOutAnItem(1, "ert-34i3");
        assertFalse(movieList.getList().get(1).getCheckOutStatus());

        assertTrue(movieList.getList().get(6).getCheckOutStatus());
        movieList.checkOutAnItem(3, "34o-45jn");
        assertFalse(movieList.getList().get(6).getCheckOutStatus());

        assertEquals(successfulCheckOutMessage + "\n" +
                successfulCheckOutMessage + "\n"
                , outContent.toString());

        movieList.removeAllItems();
    }

    @Test
    public void testReturnInvalidMovie() {
        movieList.removeAllItems();
        movieList.addToList(testMovie1);
        movieList.addToList(testMovie2);
        movieList.addToList(testMovie3);
        movieList.addToList(testMovie4);
        movieList.addToList(testMovie5);
        movieList.addToList(testMovie8);

        assertEquals(4,movieList.getUnavailableList().size());

        movieList.returnAnItem(0);
        movieList.returnAnItem(5);

        assertEquals(4,movieList.getUnavailableList().size());

        assertEquals(unsuccessfulReturnMessage + "\n" +
                unsuccessfulReturnMessage + "\n"
                , outContent.toString());

        movieList.removeAllItems();
    }

    @Test
    public void testReturnValidMovie() {
        movieList.removeAllItems();
        movieList.addToList(testMovie1);
        movieList.addToList(testMovie2);
        movieList.addToList(testMovie3);
        movieList.addToList(testMovie4);
        movieList.addToList(testMovie5);
        movieList.addToList(testMovie8);

        assertEquals(4, movieList.getUnavailableList().size());
        movieList.returnAnItem(4);
        assertEquals(3, movieList.getUnavailableList().size());

        assertEquals(successfulReturnMessage + "\n", outContent.toString());

        movieList.removeAllItems();
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}