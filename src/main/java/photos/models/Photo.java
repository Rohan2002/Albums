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

/**
 * @author Saman Sathenjeri
 * @author Rohan Deshpande
 *
 */

public class Photo implements Serializable
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
	 * Photo reference
	 */
	public String photoName;

    /**
	 * Caption of the Photo
	 */
	public String caption;

    /**
	 * List of Tags on a photo
	 */
    public ArrayList<Tag> tags;

    /**
	 * File of the picture
	 */
    public File photoFile;
	
	/**
	 * Calendar instance for date
	 */
	public Calendar calendar = new GregorianCalendar();
	
	/**
	 * Current date
	 */
	public Date date;

    /**
	 * Constructor
	 */
    public Photo (User photoUser, File photoFile, String photoName) 
    {
		if (photoFile != null) 
        {
            this.photoFile = new File(photoName);
        }
		else 
        {
            this.photoFile = photoFile;
        }

		this.tags = new ArrayList<Tag>();
		calendar.set(Calendar.MILLISECOND, 0);
		this.date = calendar.getTime();
	}

    /**
	 * Getter for caption
     * @return userName
	 */
    public String getCaption()
    {
        return this.caption;
    }

    /**
	 * Setter for caption
     * @param caption
	 */
    public void setCaption(String caption)
    {
        this.caption = caption;
    }

    /**
	 * Getter for tags
     * @return tags
	 */
    public ArrayList<Tag> getAllTags()
    {
        return this.tags;
    }

    /**
	 * Setter for tags
     * @param tags
	 */
    public void setCaption(ArrayList<Tag> tags)
    {
        this.tags = tags;
    }

    /**
	 * Getter for date
     * @return date
	 */
    public Date getDate()
    {
        return this.date;
    }

    /**
	 * Getter for Photo Name
	 * @return name of photo
	 */
	public String getName() {
		return photoName;
	}

    /**
	 * Setter for Photo Name
	 * @return name of photo
	 */
	public void setName(String photoName) {
		this.photoName = photoName;
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