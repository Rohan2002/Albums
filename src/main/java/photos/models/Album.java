package main.java.photos.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;

import main.java.photos.Photos;

/**
 * @author Saman Sathenjeri
 * @author Rohan Deshpande
 *
 */

public class Album implements Serializable
{
    /**
	 * Default Serial Number
	 */
    private static final long serialVersionUID = 1L;

    /**
	 * Store Directory
	 */
	public static final String storeDir = "dat";

    /**
	 * Store File
	 */
	public static final String storeFile = "photos.dat";

    /**
	 * Name of the Album
	 */
    public String albumName;

    /**
	 * List of Photos in an album
	 */
    public ArrayList<Photos> photos;

    /**
	 * Constructor
	 */
    public Album()
    {
        //no arg constructor
    }

    /**
	 * Constructor
     * @param albumName
	 */
    public Album(String albumName)
    {
        this.albumName = albumName;
    }

    /**
	 * Getter for list of photos in an album
     * @return photos
	 */
    public ArrayList<Photos> getPhotosInAlbum() {
        return this.photos;
    }

    /**
	 * Setter for list of photos in an album
     * @param photos
	 */
    public void setPhotosInAlbum(ArrayList<Photos> photos) {
        this.photos = photos;
    }

    /**
	 * Helper to return the number of photos in an album
     * @return photos.size()
	 */
    public int getNumOfPhotosInAlbum()
    {
        return this.photos.size();
    }

    /**
	 * Writes to the data file
	 * @throws IOException
	 */
    public static void write(User user) throws IOException 
    {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(user);
		oos.close();
	}

    /**
	 * reads in the data file
	 * @return adminListOfUsers
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
    public static Admin read() throws IOException, ClassNotFoundException
    {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		Admin adminListOfUsers = (Admin)ois.readObject(); 
        ois.close();
        return adminListOfUsers;
	} 
}