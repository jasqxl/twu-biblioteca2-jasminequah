package com.twu.biblioteca;

import java.util.*;

public interface MediaList <T extends Media> {

    public String listItems(List <T> list);
    public String detailWithSerialNumber(int serial, String detail);

    public void addToList(T newMedia);
    public void removeItem(String title, String creator, int year);
    public void removeAllItems();

    public void checkOutAnItem(int serial, String libraryNumberOfBorrower);
    public void returnAnItem(int serial);

    public List<T> getList();
}
