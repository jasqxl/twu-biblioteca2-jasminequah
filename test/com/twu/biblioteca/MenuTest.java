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
        Menu.showMenu();
        assertEquals(welcomeMessage + "\n" + menuHeading + 
                "\n1) List Books\n2) List Movies\n\nEnter number of choice here: ", outContent.toString());
    }

    @Test
    public void testCloseProgram() {
        Menu.openProgram();
        outContent.reset();
        Menu.closeProgram();
        assertEquals(goodbyeMessage + "\n", outContent.toString());
    }

    @Test
    public void testGetOption() {
        Menu.removeAllOptions();
        Menu.openProgram();

        assertEquals( 2, Menu.getMenu().size());
        assertEquals( "List Books", Menu.getMenu().get(0));
        assertEquals( "List Movies", Menu.getMenu().get(1));

        Menu.addOptionsToFile("Check out book");
        Menu.addOptionsToFile("Return book");
        Menu.addOptionsToFile("Quit");

        assertEquals( 5, Menu.getMenu().size());
        assertEquals( "List Books", Menu.getMenu().get(0));
        assertEquals( "List Movies", Menu.getMenu().get(1));
        assertEquals( "Check out book", Menu.getMenu().get(2));
        assertEquals( "Return book", Menu.getMenu().get(3));
        assertEquals( "Quit", Menu.getMenu().get(4));

        Menu.removeAllOptions();
    }

    @Test
    public void testGetActionMenu() {
        Menu.openProgram();
        Menu.showActionMenu();

        assertEquals( 2, Menu.getActionMenu().size());
        assertEquals( "Check out item", Menu.getActionMenu().get(0));
        assertEquals("Return item", Menu.getActionMenu().get(1));

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

        assertEquals( 4, Menu.getMenu().size());
        assertEquals( "List Books", Menu.getMenu().get(0));
        assertEquals( "List Movies", Menu.getMenu().get(1));
        assertEquals( "Return book", Menu.getMenu().get(2));
        assertEquals( "Quit", Menu.getMenu().get(3));

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

        assertEquals( 0, Menu.getMenu().size());
    }

    @Test
    public void testAddOneOption() {
        Menu.removeAllOptions();
        Menu.openProgram();
        Menu.addOptionsToFile("Check out book");

        assertEquals( 3, Menu.getMenu().size());
        assertEquals( "List Books", Menu.getMenu().get(0));
        assertEquals( "List Movies", Menu.getMenu().get(1));
        assertEquals( "Check out book", Menu.getMenu().get(2));

        Menu.removeAllOptions();
    }

    @Test
    public void testAddMoreThanOneOption() {
        Menu.removeAllOptions();
        Menu.openProgram();
        Menu.addOptionsToFile("Check out book");
        Menu.addOptionsToFile("Return book");
        Menu.addOptionsToFile("Quit");

        assertEquals( 5, Menu.getMenu().size());
        assertEquals( "List Books", Menu.getMenu().get(0));
        assertEquals( "List Movies", Menu.getMenu().get(1));
        assertEquals( "Check out book", Menu.getMenu().get(2));
        assertEquals( "Return book", Menu.getMenu().get(3));
        assertEquals( "Quit", Menu.getMenu().get(4));

        Menu.removeAllOptions();
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}