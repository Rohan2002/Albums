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

/**
 * @author Saman Sathenjeri
 * @author Rohan Deshpande
 *
 */

public class Admin implements Serializable
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
	 * List of Users on platform
	 */
    public ArrayList<User> users;

    /**
	 * Constructor
	 */
    public Admin()
    {
        //no arg constructor
    }

    /**
	 * Getter for list of photos in an album
     * @return users
	 */
    public ArrayList<User> getUsers() {
        return this.users;
    }

    /**
	 * Setter for list of users
     * @param users
	 */
    public void setUsers(ArrayList<User> users) {
        this.users = users;
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
