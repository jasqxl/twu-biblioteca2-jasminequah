package com.twu.biblioteca;

import org.junit.*;
import java.io.*;
import java.util.*;
import static com.twu.biblioteca.BibliotecaApp.*;
import static org.junit.Assert.*;

public class BibliotecaAppTest {

    private String invalidMenuOptionMessage = "Select a valid option!\n";

    private List<String> options = new ArrayList<String>();

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

        checkUserChoice("quit", options.size());
        assertEquals("", outContent.toString());

        outContent.reset();

        checkUserChoice("Quit", options.size());
        assertEquals("", outContent.toString());
    }

    @Test
    public void testInvalidOption() {
        options.clear();
        options.add("List Books");
        options.add("Check out books");
        options.add("Return book");

        checkUserChoice("0", options.size());
        assertEquals(invalidMenuOptionMessage + "\n", outContent.toString());

        outContent.reset();

        checkUserChoice("dg f", options.size());
        assertEquals(invalidMenuOptionMessage + "\n", outContent.toString());

        outContent.reset();

        checkUserChoice("-2", options.size());
        assertEquals(invalidMenuOptionMessage + "\n", outContent.toString());
    }

    @Test
    public void testValidOption() {
        options.clear();
        options.add("List Books");
        options.add("Check out books");
        options.add("Return book");

        checkUserChoice("2", options.size());
        assertEquals("", outContent.toString());

        outContent.reset();

        checkUserChoice("3", options.size());
        assertEquals("", outContent.toString());
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}