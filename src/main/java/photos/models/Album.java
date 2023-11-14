package main.java.photos.models;

import java.util.ArrayList;

import main.java.photos.Photos;

public class Album {
    public String albumName;
    public ArrayList<Photos> photos;

    public Album()
    {
        //no arg constructor
    }

    public Album(String albumName)
    {
        this.albumName = albumName;
    }

    public ArrayList<Photos> getPhotosInAlbum() {
        return this.photos;
    }

    public void setPhotosInAlbum(ArrayList<Photos> photos) {
        this.photos = photos;
    }
}