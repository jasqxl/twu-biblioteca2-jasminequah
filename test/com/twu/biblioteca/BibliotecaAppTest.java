package com.twu.biblioteca;

import org.junit.*;
import java.io.*;
import java.util.*;

import static com.twu.biblioteca.BibliotecaApp.checkUserChoice;
import static com.twu.biblioteca.BibliotecaApp.parseOption;
import static org.junit.Assert.*;

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
    public void testCheckUserChoice() {
        options.add("List Books");
        options.add("Check out books");
        options.add("Return book");

        checkUserChoice("quit", options);
        assertEquals("", outContent.toString());

        outContent.reset();

        checkUserChoice("0", options);
        assertEquals(invalidMenuOptionMessage + "\n", outContent.toString());

        outContent.reset();

        checkUserChoice("-2", options);
        assertEquals(invalidMenuOptionMessage + "\n", outContent.toString());

        outContent.reset();

        checkUserChoice("2", options);
        assertEquals("", outContent.toString());
    }

    @Test
    public void testParseOption() {
        options.add("List Books");
        options.add("Check out books");
        options.add("Return book");

        assertEquals(0, parseOption("quit", options));
        assertEquals(0, parseOption("Quit", options));

        assertEquals(-1, parseOption("adsf", options));
        assertEquals(-1, parseOption("List Book", options));

        assertEquals(1, parseOption("1", options));
        assertEquals(3, parseOption("3", options));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}