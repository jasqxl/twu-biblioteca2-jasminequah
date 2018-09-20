package com.twu.biblioteca;

public interface Media {

    public String getTitle();
    public String getCreator();
    public int getReleaseYear();
    public boolean getCheckOutStatus();

    public String listDetail();
    public String listAllDetail();


    public void checkOutItem();
    public void returnItem();
}
