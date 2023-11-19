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

import main.java.photos.utils.ErrorCode;
import main.java.photos.utils.ErrorMessage;

/**
 * @author Saman Sathenjeri
 * @author Rohan Deshpande
 *
 */

public class Album implements Serializable
{
    // /**
	//  * Default Serial Number
	//  */
    // private static final long serialVersionUID = 1L;

    // /**
	//  * Store Directory
	//  */
	// public static final String storeDir = "dat";

    // /**
	//  * Store File
	//  */
	// public static final String storeFile = "photos.dat";

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
    public ArrayList<Photo> getPhotosInAlbum() {
        return this.photos;
    }

    /**
	 * Setter for list of photos in an album
     * @param photos
	 */
    public void setPhotosInAlbum(ArrayList<Photo> photos) {
        this.photos = photos;
    }

    /**
	 * Helper to add photo to album
     * @param photos
	 */
    public void addPhoto(Photo photo) {
        this.getPhotosInAlbum().add(photo);
    }

    /**
	 * Helper to delete a photo to album
     * @param photos
	 */
    public void deletePhoto(Photo photo) 
    {   
        if (this.getPhotosInAlbum().contains(photo))
        {
            this.getPhotosInAlbum().remove(photo);
        }
    }

    /**
	 * Helper to return the number of photos in an album
     * @return photos.size()
	 */
    public int getNumOfPhotosInAlbum()
    {
        if (photos == null)
        {
            return 0;
        }
        return this.photos.size();
    }

    /**
	 * Getter for album name
     * @return albumName
	 */
    public String getAlbumName() {
        return this.albumName;
    }

    /**
	 * Setter for albumName
     * @param albumName
	 */
    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    /**
	 * Helper to get min and max dates in an album
     * @return dates
	 */
    public String getAlbumDateRange()
    {
        if (photos == null)
        {
            return "N/A";
        }
        return getMinDate() + " - " + getMaxDate();
    }

    /**
	 * Helper to get min date in an album
     * @return date
	 */
    public String getMinDate()
    {
        Date date = photos.get(0).date;
        for(int i = 1; i < photos.size(); i++)
        {
            if (photos.get(i).date.before(date))
            {
                date = photos.get(i).date;
            }
        }
        return toString(date);
    }

    /**
	 * Helper to get min and max dates in an album
     * @return date
	 */
    public String getMaxDate()
    {
        Date date = photos.get(0).date;
        for(int i = 1; i < photos.size(); i++)
        {
            if (photos.get(i).date.after(date))
            {
                date = photos.get(i).date;
            }
        }
        return toString(date);
    }

    /**
	 * Override of toString for dates
     * @return date
	 */
    private String toString(Date date) 
    {
        String pattern = "MM/dd/yyyy";
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    /**
     * Checks if there are duplicate photos with the same filepath
     * @param photo
     * @return
     */
    public boolean duplicatePhoto (Photo photo)
    {
        for(int y = 0; y < photos.size(); y++) {
            if(photos.get(y) != null && photos.get(y).getFile().compareTo(photo.getFile()) == 0) {
                return true;
            }
        }
        return false;
    }

    public int getIndexOfPhoto(Photo photo)
    {
        for (int i = 0; i < this.getNumOfPhotosInAlbum(); i++)
        {
            if (this.getPhotosInAlbum().get(i).equals(photo))
            {
                return i;
            }
        }
        return -1;
    }

    // /**
	//  * Writes to the data file
	//  * @throws IOException
	//  */
    // public static void write(User user) throws IOException 
    // {
	// 	ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
	// 	oos.writeObject(user);
	// 	oos.close();
	// }

    // /**
	//  * reads in the data file
	//  * @return adminListOfUsers
	//  * @throws IOException
	//  * @throws ClassNotFoundException
	//  */
    // public static Admin read() throws IOException, ClassNotFoundException
    // {
	// 	ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
	// 	Admin adminListOfUsers = (Admin)ois.readObject(); 
    //     ois.close();
    //     return adminListOfUsers;
	// } 
}