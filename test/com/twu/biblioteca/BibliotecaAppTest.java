package com.twu.biblioteca;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static com.twu.biblioteca.BibliotecaApp.checkUserChoice;
import static org.junit.Assert.assertEquals;

public class BibliotecaAppTest {

    private static String invalidMenuOptionMessage = "Select a valid option!\n";

    private static List<String> options = new ArrayList<String>();

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
            System.setOut(new PrintStream(outContent));
        }

    @Test
    public void testQuit() {
        options.clear();
        options.add("List Books");
        options.add("Check out books");
        options.add("Return book");

        checkUserChoice("quit", options);
        assertEquals("", outContent.toString());

        outContent.reset();

        checkUserChoice("Quit", options);
        assertEquals("", outContent.toString());
    }

    @Test
    public void testInvalidOption() {
        options.clear();
        options.add("List Books");
        options.add("Check out books");
        options.add("Return book");

        checkUserChoice("0", options);
        assertEquals(invalidMenuOptionMessage + "\n", outContent.toString());

        outContent.reset();

        checkUserChoice("dg f", options);
        assertEquals(invalidMenuOptionMessage + "\n", outContent.toString());

        outContent.reset();

        checkUserChoice("-2", options);
        assertEquals(invalidMenuOptionMessage + "\n", outContent.toString());
    }

    @Test
    public void testValidOption() {
        options.clear();
        options.add("List Books");
        options.add("Check out books");
        options.add("Return book");

        checkUserChoice("2", options);
        assertEquals("", outContent.toString());

        outContent.reset();

        checkUserChoice("3", options);
        assertEquals("", outContent.toString());
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}