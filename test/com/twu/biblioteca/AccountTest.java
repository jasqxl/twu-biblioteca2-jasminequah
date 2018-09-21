package com.twu.biblioteca;

import org.junit.*;
import java.io.*;
import java.util.*;

import static org.junit.Assert.*;

public class AccountTest {

    private static String unsuccessfulLoginMessage = "The account number and password provided does not exist.\n";
    private static String successfulLoginMessage = "You have successfully logged into Biblioteca.\n";

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private static List<String> accountDetails = new ArrayList<String>();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testInvalidLoginAccount() {
        accountDetails = Account.loginAccount("dfvsd", "sdvsdcw");
        assertEquals(unsuccessfulLoginMessage + "\n", outContent.toString());
    }

    @Test
    public void testValidLoginUserAccount() {
        accountDetails = Account.loginAccount("329-439j", "password");
        assertEquals(successfulLoginMessage + "\n", outContent.toString());
        assertEquals("Jasmine dfbs", accountDetails.get(0));
        assertEquals("hey-loyd@hotmail.com", accountDetails.get(1));
        assertEquals("85238909", accountDetails.get(2));
        assertEquals("User", accountDetails.get(5));
    }

    @Test
    public void testValidLoginLibrarianAccount() {
        accountDetails = Account.loginAccount("28y-gy42", "ouhsdochi");
        assertEquals(successfulLoginMessage + "\n", outContent.toString());
        assertEquals("Kenny boo", accountDetails.get(0));
        assertEquals("no.sorry@gmail.com", accountDetails.get(1));
        assertEquals("98243699", accountDetails.get(2));
        assertEquals("Librarian", accountDetails.get(5));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}