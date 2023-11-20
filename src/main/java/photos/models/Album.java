/**
 * This class represents the Album object. 
 * 
 * An album will have a list of Photo. Each user will have a list of Album.
 * @author Saman Sathenjeri
 * @author Rohan Deshpande
 * @version 1.0
 */
package main.java.photos.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

import main.java.photos.utils.ErrorCode;
import main.java.photos.utils.ErrorMessage;

public class Album implements Serializable {
    /**
     * Name of the Album
     */
    public String albumName;

    /**
     * List of Photos in an album
     */
    public ArrayList<Photo> photos = new ArrayList<Photo>();

    /**
     * Constructor
     */
    public Album() {
        // no arg constructor
    }

    /**
     * Constructor
     * 
     * @param albumName
     */
    public Album(String albumName) {
        this.albumName = albumName;
    }

    /**
     * Getter for list of photos in an album
     * 
     * @return photos
     */
    public ArrayList<Photo> getPhotosInAlbum() {
        return this.photos;
    }

    /**
     * Setter for list of photos in an album
     * 
     * @param photos
     */
    public void setPhotosInAlbum(ArrayList<Photo> photos) {
        this.photos = photos;
    }

    /**
     * Helper to add photo to album
     * 
     * @param photos
     */
    public void addPhoto(Photo photo) {
        if (!this.duplicatePhoto(photo)) {
            this.getPhotosInAlbum().add(photo);
        } else {
            ErrorMessage.showError(ErrorCode.AUTHERROR, "Duplicate Photo",
                    "Choose Another Photo");
        }
    }

    /**
     * Helper to delete a photo to album
     * 
     * @param photos
     */
    public void deletePhoto(Photo photo) {
        if (this.getPhotosInAlbum().contains(photo)) {
            this.getPhotosInAlbum().remove(photo);
        }
    }

    /**
     * Helper to return the number of photos in an album
     * 
     * @return photos.size()
     */
    public int getNumOfPhotosInAlbum() {
        if (photos == null) {
            return 0;
        }
        return this.photos.size();
    }

    /**
     * Getter for album name
     * 
     * @return albumName
     */
    public String getAlbumName() {
        return this.albumName != null ? this.albumName : "";
    }

    /**
     * Setter for albumName
     * 
     * @param albumName
     */
    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    /**
     * Helper to get min and max dates in an album
     * 
     * @return dates
     */
    public String getAlbumDateRange() {
        if (photos == null || (photos != null && photos.size() < 1)) {
            return "N/A";
        }
        return getMinDate() + " - " + getMaxDate();
    }

    /**
     * Helper to get min date in an album
     * 
     * @return date
     */
    public String getMinDate() {
        Date date = photos.get(0).date;
        for (int i = 1; i < photos.size(); i++) {
            if (photos.get(i).date.before(date)) {
                date = photos.get(i).date;
            }
        }
        return dateToString(date);
    }

    /**
     * Helper to get min and max dates in an album
     * 
     * @return date
     */
    public String getMaxDate() {
        Date date = photos.get(0).date;
        for (int i = 1; i < photos.size(); i++) {
            if (photos.get(i).date.after(date)) {
                date = photos.get(i).date;
            }
        }
        return dateToString(date);
    }

    /**
     * Override of dateToString for dates
     * 
     * @return date
     */
    private String dateToString(Date date) {
        String pattern = "MM/dd/yyyy";
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    /**
     * Checks if there are duplicate photos with the same filepath
     * 
     * @param photo
     * @return
     */
    public boolean duplicatePhoto(Photo photo) {
        for (int y = 0; y < photos.size(); y++) {
            if (photos.get(y) != null && photos.get(y).getFile().compareTo(photo.getFile()) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * gets index of a photo
     * 
     * @param photo
     * @return
     */
    public int getIndexOfPhoto(Photo photo) {
        for (int i = 0; i < this.getNumOfPhotosInAlbum(); i++) {
            if (this.getPhotosInAlbum().get(i).equals(photo)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * returns the next photo in the album
     * 
     * @param photo
     * @return
     */
    public Photo getNextPhoto(Photo photo) {
        int index = getIndexOfPhoto(photo);
        index++;
        if (index < this.getNumOfPhotosInAlbum()) {
            return this.getPhotosInAlbum().get(index);
        }
        return null;
    }

    /**
     * returns the previous photo in the album
     * 
     * @param photo
     * @return
     */
    public Photo getPreviousPhoto(Photo photo) {
        int index = getIndexOfPhoto(photo);
        index--;
        if (index >= 0) {
            return this.getPhotosInAlbum().get(index);
        }
        return null;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return this.albumName;
    }

    public Album searchByDate(Date date) {
        Album returnAlbum = new Album();
        for (int i = 0; i < photos.size(); i++) {
            if (photos.get(i).date.equals(date)) {
                returnAlbum.addPhoto(photos.get(i));
            }
        }
        return returnAlbum;
    }
}