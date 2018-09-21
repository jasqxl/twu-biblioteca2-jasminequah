package com.twu.biblioteca;

public interface Media {

    public String getTitle();
    public String getCreator();
    public int getReleaseYear();
    public String getBorrowerAccountNumber();
    public boolean getCheckOutStatus();

    public String listDetail();
    public String listAllDetail();

    public void checkOutItem(String libraryNumber);
    public void returnItem();
}
