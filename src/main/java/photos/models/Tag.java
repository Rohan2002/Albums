package main.java.photos.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

/**
 * @author Saman Sathenjeri
 * @author Rohan Deshpande
 *
 */

public class Tag implements Serializable
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
     * name of tag
     */
    String tagName;

    /**
     * data of tag
     */
    String tagData;

    /**
     * Constructor
     * @param totalTag
     */
    public Tag(String totalTag)
    {
        String[] arrOfStr = totalTag.split(":", 2);
        this.tagName = arrOfStr[0];
        this.tagData = arrOfStr[1];
    }

    /**
     * Constuctor
     * @param tagName
     * @param tagData
     */
    public Tag(String tagName, String tagData)
    {
        this.tagName = tagName;
        this.tagData = tagData;
    }

    /**
     * Getter for Tag Name
     * @return
     */
    public String getTagName()
    {
        return this.tagName;
    }

    /**
     * Setter for Tag Name
     * @param tagName
     * @return
     */
    public void setTagName(String tagName)
    {
        this.tagName = tagName;
    }

    /**
     * Getter for Tag Data
     * @return
     */
    public String getTagData()
    {
        return this.tagData;
    }

    /**
     * Setter for Tag Data
     * @param tagData
     * @return
     */
    public void setTagData(String tagData)
    {
        this.tagData = tagData;
    }

    /**
     * Helper to return tag as string
     * @return
     */
    public String tagAsString()
    {
        return this.getTagName() + ":" + this.getTagData();
    }
    
    /**
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