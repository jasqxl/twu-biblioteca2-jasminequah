package com.twu.biblioteca;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

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
        assertEquals(welcomeMessage + "\n" + menuHeading + 
                "\n1) List Books\n2) List Movies\n\nEnter choice here: ", outContent.toString());
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
        assertEquals(menuHeading + "\n1) List Books\n2) List Movies\n\nEnter choice here: "
                , outContent.toString());
    }

    @Test
    public void testGetOption() {
        Menu.removeAllOptions();
        Menu.openProgram();

        assertEquals( 2, Menu.getOptions().size());
        assertEquals( "List Books", Menu.getOptions().get(0));
        assertEquals( "List Movies", Menu.getOptions().get(1));

        Menu.addOptionsToFile("Check out book");
        Menu.addOptionsToFile("Return book");
        Menu.addOptionsToFile("Quit");

        assertEquals( 5, Menu.getOptions().size());
        assertEquals( "List Books", Menu.getOptions().get(0));
        assertEquals( "List Movies", Menu.getOptions().get(1));
        assertEquals( "Check out book", Menu.getOptions().get(2));
        assertEquals( "Return book", Menu.getOptions().get(3));
        assertEquals( "Quit", Menu.getOptions().get(4));

        Menu.removeAllOptions();
    }

    @Test
    public void testRemoveOneOption() {
        Menu.removeAllOptions();
        Menu.openProgram();
        Menu.addOptionsToFile("Check out book");
        Menu.addOptionsToFile("Return book");
        Menu.addOptionsToFile("Quit");

        Menu.removeOption("Check out book");

        assertEquals( 4, Menu.getOptions().size());
        assertEquals( "List Books", Menu.getOptions().get(0));
        assertEquals( "List Movies", Menu.getOptions().get(1));
        assertEquals( "Return book", Menu.getOptions().get(2));
        assertEquals( "Quit", Menu.getOptions().get(3));

        Menu.removeAllOptions();
    }

    @Test
    public void testRemoveAllOption() {
        Menu.removeAllOptions();
        Menu.openProgram();
        Menu.addOptionsToFile("Check out book");
        Menu.addOptionsToFile("Return book");
        Menu.addOptionsToFile("Quit");
        
        Menu.removeAllOptions();

        assertEquals( 0, Menu.getOptions().size());
    }

    @Test
    public void testAddOneOption() {
        Menu.removeAllOptions();
        Menu.openProgram();
        Menu.addOptionsToFile("Check out book");

        assertEquals( 3, Menu.getOptions().size());
        assertEquals( "List Books", Menu.getOptions().get(0));
        assertEquals( "List Movies", Menu.getOptions().get(1));
        assertEquals( "Check out book", Menu.getOptions().get(2));

        Menu.removeAllOptions();
    }

    @Test
    public void testAddMoreThanOneOption() {
        Menu.removeAllOptions();
        Menu.openProgram();
        Menu.addOptionsToFile("Check out book");
        Menu.addOptionsToFile("Return book");
        Menu.addOptionsToFile("Quit");

        assertEquals( 5, Menu.getOptions().size());
        assertEquals( "List Books", Menu.getOptions().get(0));
        assertEquals( "List Movies", Menu.getOptions().get(1));
        assertEquals( "Check out book", Menu.getOptions().get(2));
        assertEquals( "Return book", Menu.getOptions().get(3));
        assertEquals( "Quit", Menu.getOptions().get(4));

        Menu.removeAllOptions();
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}