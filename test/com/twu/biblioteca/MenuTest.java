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
                "\n1) List Books\n", outContent.toString());
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
        assertEquals(menuHeading + "\n1) List Books\n"
                , outContent.toString());
    }

    @Test
    public void testGetOption() {
        Menu.removeAllOptions();
        Menu.openProgram();

        assertEquals( 1, Menu.getOptions().size());
        assertEquals( "List Books", Menu.getOptions().get(0));

        Menu.addOption("Check out book");
        Menu.addOption("Return book");
        Menu.addOption("Quit");

        assertEquals( 4, Menu.getOptions().size());
        assertEquals( "List Books", Menu.getOptions().get(0));
        assertEquals( "Check out book", Menu.getOptions().get(1));
        assertEquals( "Return book", Menu.getOptions().get(2));
        assertEquals( "Quit", Menu.getOptions().get(3));

        Menu.removeAllOptions();
    }

    @Test
    public void testRemoveOneOption() {
        Menu.removeAllOptions();
        Menu.openProgram();
        Menu.addOption("Check out book");
        Menu.addOption("Return book");
        Menu.addOption("Quit");

        Menu.removeOptions("Check out book");

        assertEquals( 3, Menu.getOptions().size());
        assertEquals( "List Books", Menu.getOptions().get(0));
        assertEquals( "Return book", Menu.getOptions().get(1));
        assertEquals( "Quit", Menu.getOptions().get(2));

        Menu.removeAllOptions();
    }
    @Test
    public void testRemoveAllOption() {
        Menu.removeAllOptions();
        Menu.openProgram();
        Menu.addOption("Check out book");
        Menu.addOption("Return book");
        Menu.addOption("Quit");
        
        Menu.removeAllOptions();

        assertEquals( 1, Menu.getOptions().size());
        assertEquals( "List Books", Menu.getOptions().get(0));
    }

    @Test
    public void testAddOneOption() {
        Menu.removeAllOptions();
        Menu.openProgram();
        Menu.addOption("Check out book");

        assertEquals( 2, Menu.getOptions().size());
        assertEquals( "List Books", Menu.getOptions().get(0));
        assertEquals( "Check out book", Menu.getOptions().get(1));

        Menu.removeAllOptions();
    }

    @Test
    public void testAddMoreThanOneOption() {
        Menu.removeAllOptions();
        Menu.openProgram();
        Menu.addOption("Check out book");
        Menu.addOption("Return book");
        Menu.addOption("Quit");

        assertEquals( 4, Menu.getOptions().size());
        assertEquals( "List Books", Menu.getOptions().get(0));
        assertEquals( "Check out book", Menu.getOptions().get(1));
        assertEquals( "List Books", Menu.getOptions().get(0));
        assertEquals( "Check out book", Menu.getOptions().get(1));
        assertEquals( "Return book", Menu.getOptions().get(2));
        assertEquals( "Quit", Menu.getOptions().get(3));

        Menu.removeAllOptions();
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}