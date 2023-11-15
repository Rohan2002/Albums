/**
 * This class will store the user schema.
 * We store the username, password and userDirectory inside this object.
 * This password is currently stored in plain-text format. One future improvement
 * will be to add some encryption to the password string.
 * 
 * @author Rohan Deshpande
 * @version 1.0
 */

package main.java.photos.models;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import main.java.photos.utils.Tools;

public class User implements Serializable {

    private String username;
    private String password;
    private File userDirectory;

    /**
     * The user object needs the username and password. 
     * The userDirectory is later created via the initUser routine.
     * The userDirectory will contain all the user's app data.
     * @param u The username string
     * @param p The password string
     */
    public User(String u, String p) {
        this.username = u;
        this.password = p;
        this.userDirectory = null;
    }

    /**
     * Creates the user's app data directory.
     * @throws IOException Directory cannot be created.
     */
    public void initUser() throws IOException {
        File dataDirFileObj = Tools.getDataDir();

        String userDirectory = dataDirFileObj.getPath() + File.separator + this.username;
        File userDirFileObj = new File(userDirectory);
        if (!userDirFileObj.isDirectory() && !userDirFileObj.mkdir()) {
            throw new IOException("Cannot create user directory: " + userDirectory);
        }
        this.userDirectory = userDirFileObj;
    }
    
    /**
     * Username getter function
     * @return the username of the user
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Username getter function
     * @return the username of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Password getter function
     * @return the password of the user
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Password setter
     * @param password String password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * User directory getter
     * @return the user directory
     */
    public File getUserDirectory() {
        return this.userDirectory;
    }

    /**
     * To string of the User object. 
     */
    @Override
    public String toString() {
        return getUsername();
    }

    /**
     * Equals comparison of the User object.
     * Equality of strings is based on username and password fields only.
     * Note for username, the case of the string does not matter, but for password it does.
     */
    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof User)) {
            return false;
        }
        User cObject = (User) object;
        return this.username.equalsIgnoreCase(cObject.username) && this.password.equals(cObject.password);
    }

}
