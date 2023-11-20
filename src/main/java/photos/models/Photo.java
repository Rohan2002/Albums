package main.java.photos.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import main.java.photos.utils.ErrorCode;
import main.java.photos.utils.ErrorMessage;

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
    public Photo (User photoUser, File photoFile) 
    {
		if (photoFile == null) 
        {
            //this.photoFile = new File();
        }
		else 
        {
            this.photoFile = photoFile;
        }

		this.tags = new ArrayList<Tag>();
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
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
    public void setAllTags(ArrayList<Tag> tags)
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

	/**
	 * Getter for Photo File
	 * @return file of Photo
	 */
	public File getFile() {
		return photoFile;
	}

    /**
	 * Setter for Photo Name
	 * @return name of photo
	 */
	public void setFile(File file) {
		this.photoFile = file;
	}

	/**
	 * Helper to add tags to a photos tag list
	 * @param tag
	 */
	public void addTag(Tag tag)
	{
		this.getAllTags().add(tag);
	}

	/**
	 * Helper to remove tags to a photos tag list
	 * @param tag
	 */
	public void removeTag(Tag tag)
	{
		this.getAllTags().remove(tag);
	}

	/**
	 * Helper to find tag
	 * @param tag
	 * @return tag
	 */
	public Tag findTag(Tag tag)
	{
		for (int i = 0; i < tags.size(); i++)
		{
			if (tags.get(i).getTagName().equalsIgnoreCase(tag.getTagName()) && 
				tags.get(i).getTagData().equalsIgnoreCase(tag.getTagData()))
			{
				return tags.get(i);
			}
		}
		return null;
	}

	/**
	 * 
	 */
	public String setTagsToString()
	{
		String totalString = "";
		for (int i = 0; i < tags.size(); i++)
		{
			totalString = totalString + tags.get(i).tagAsString() + ", ";
		}
		return totalString;
	}

	public String dateToString(Date date)
	{
		if (date != null)
		{
			String pattern = "MM/dd/yyyy";
			DateFormat df = new SimpleDateFormat(pattern);
			String todayAsString = df.format(date);
			return todayAsString;
		}
		else
		{
			return "";
		}
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