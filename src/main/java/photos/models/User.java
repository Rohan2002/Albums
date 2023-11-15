package main.java.photos.models;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import main.java.photos.utils.Tools;

public class User implements Serializable {

    private String username;
    private String password;
    private File userDirectory;

    public User(String u, String p) {
        this.username = u;
        this.password = p;
        this.userDirectory = null;
    }

    public void initUser() throws IOException {
        File dataDirFileObj = Tools.getDataDir();

        String userDirectory = dataDirFileObj.getPath() + File.separator + this.username;
        File userDirFileObj = new File(userDirectory);
        if (!userDirFileObj.isDirectory() && !userDirFileObj.mkdir()) {
            throw new IOException("Cannot create user directory: " + userDirectory);
        }
        this.userDirectory = userDirFileObj;
    }
    
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public File getUserDirectory() {
        return this.userDirectory;
    }

    @Override
    public String toString() {
        return getUsername();
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof User)) {
            return false;
        }
        User cObject = (User) object;
        return this.username.equalsIgnoreCase(cObject.username) && this.password.equals(cObject.password);
    }

}
