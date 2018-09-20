package com.twu.biblioteca;

import org.junit.*;
import java.io.*;
import java.util.*;
import static org.junit.Assert.*;

public class MenuTest {

    private static String welcomeMessage = "Welcome to Biblioteca :)\n";
    private static String goodbyeMessage = "Thank you for using Biblioteca..\n";
    private static String menuHeading = "Please choose an action from the list below:";

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testOpenProgram() {
        Menu.removeAllOptions();
        Menu.openProgram();
        assertEquals(welcomeMessage + "\n" + menuHeading + "\n1) List Books\n", outContent.toString());
    }

    @Test
    public void testCloseProgram() {
        Menu.closeProgram();
        assertEquals(goodbyeMessage + "\n", outContent.toString());
    }

    @Test
    public void testShowMenu() {
        Menu.removeAllOptions();
        Menu.showMenu();
        assertEquals(menuHeading + "\n1) List Books\n", outContent.toString());
    }

    @Test
    public void testGetOption() {
        List<String> options = new ArrayList<String>();

        assertEquals( 0, options.size());

        Menu.removeAllOptions();
        Menu.openProgram();
        options = Menu.getOptions();

        assertEquals( 1, options.size());
        assertEquals( "List Books", options.get(0));
    }

    @Test
    public void testRemoveOption() {
        List<String> options = new ArrayList<String>();

        Menu.removeAllOptions();
        Menu.openProgram();
        Menu.addOption("Check out book");
        Menu.addOption("Return book");
        Menu.addOption("Quit");
        options = Menu.getOptions();

        assertEquals( 4, options.size());
        assertEquals( "List Books", options.get(0));
        assertEquals( "Check out book", options.get(1));
        assertEquals( "Return book", options.get(2));
        assertEquals( "Quit", options.get(3));

        Menu.removeOptions("Check out book");
        options = Menu.getOptions();

        assertEquals( 3, options.size());
        assertEquals( "List Books", options.get(0));
        assertEquals( "Return book", options.get(1));
        assertEquals( "Quit", options.get(2));

        Menu.removeAllOptions();
        options = Menu.getOptions();

        assertEquals( 1, options.size());
        assertEquals( "List Books", options.get(0));
    }

    @Test
    public void testAddOption() {
        List<String> options = new ArrayList<String>();
        Menu.removeAllOptions();
        Menu.openProgram();
        Menu.addOption("Check out book");
        options = Menu.getOptions();

        assertEquals( 2, options.size());
        assertEquals( "List Books", options.get(0));
        assertEquals( "Check out book", options.get(1));

        Menu.addOption("Return book");
        Menu.addOption("Quit");
        options = Menu.getOptions();

        assertEquals( 4, options.size());
        assertEquals( "List Books", options.get(0));
        assertEquals( "Check out book", options.get(1));
        assertEquals( "Return book", options.get(2));
        assertEquals( "Quit", options.get(3));

        Menu.removeAllOptions();
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}