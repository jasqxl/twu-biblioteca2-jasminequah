package com.twu.biblioteca;

import java.util.*;

public interface MediaList <T extends Media> {

    public void retrieveList ();

    public void addToList(T newMedia);
    public void removeItem(String title, String creator, int year);
    public void removeAllItems();


    public void checkOutAnItem(int serial, int libraryNumberOfBorrower);
    public void returnAnItem(String title, String creator, int year);

    public List<T> getList();
}
