/**
 * The User list class will list all the "User" objects.
 * The list is an arraylist. 
 * 
 * Since the list needs a write to disk all the time, so we also
 * read the list from disk for all routines to maintain data consistency.
 * 
 * @author Rohan Deshpande
 * @version 1.0
 */
package main.java.photos.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import main.java.photos.utils.ErrorCode;
import main.java.photos.utils.ErrorMessage;
import main.java.photos.utils.Tools;

public class UserList {
    private String userListLocation;

    /**
     * Store the location of the user list on disk.
     * @throws IOException User list location on disk was not found.
     */
    public UserList() throws IOException {
        this.userListLocation = Tools.getDataDir().getPath() + File.separator + "ulist.ser";
        File userListLocationFile = new File(this.userListLocation);
        if(!userListLocationFile.exists()){
            userListLocationFile.createNewFile();
        }
    }

    /**
     * Read user list from disk
     * @return List
     */
    public List<User> getUserList() {
        return fetchUserList();
    }
    
    /*
     * Get user list location on disk
     */
    public String getUserListLocation() {
        return this.userListLocation;
    }

    /**
     * Fetch user list from disk and check if the User u exists.
     * @param u User
     * @return True if the User u exists else False.
     */
    public boolean userExists(User u) {
        return fetchUserList().contains(u);
    }

    /**
     * Read user list from disk, update list, and then write updated list to disk
     * @param u User
     * @return True if user was added to the list successfully else False
     */
    public boolean addUser(User u) {
        ArrayList<User> userList = fetchUserList();
        if (userExists(u)) {
            return false;
        }
        if (userList.add(u)) {
            return saveUserList(userList);
        }
        ;
        return false;
    }

    /**
     * Read user list from disk, update list, then write updated list of disk
     * @param u User
     * @return True if the user was deleted from list else False
     */
    public boolean deleteUser(User u) {
        ArrayList<User> userList = fetchUserList();
        if (userList.remove(u)) {
            return saveUserList(userList);
        }
        return false;
    }

    /**
     * Read user list from disk, update list, and then write updated list to disk.
     * @param usernameToDelete The String username only based search
     * @return True if the user was deleted based on the username
     */
    public boolean deleteUser(String usernameToDelete) {
        ArrayList<User> userList = fetchUserList();
        boolean removedUser = false;
        Iterator<User> iterator = userList.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getUsername().equalsIgnoreCase(usernameToDelete)) {
                iterator.remove();
                removedUser = saveUserList(userList);
            }
        }
        return removedUser;
    }

    /**
     * Update user list on disk with updated user object.
     * 
     * @param updatedUser
     * @return true if User was updated successfully else false.
     */
    public boolean updateUser(User updatedUser){
        ArrayList<User> u = fetchUserList();

        /**
         * 
         * The username and password determines if two users are the same or not.
         * If two users have the same username and password but have different
         * albums then we say that the user is actually updated rather than replaced.
         * So a simple indexOf with the updatedUser object is enough to find the user to update.
         * becaused indexOf relies on the User's equality method.
         */

        int oldUserIndex = u.indexOf(updatedUser);
        u.set(oldUserIndex, updatedUser);
        boolean saveUserListStatus = saveUserList(u);
        return saveUserListStatus;
    }

    /**
     * A routine to check if there is a user with 
     * username and password in the list. If not exists
     * then return null for that user.
     * @return
     */
    public User getUser(String username, String password){
        ArrayList<User> users = this.fetchUserList();
        for(User u: users){
            if(username.equalsIgnoreCase(u.getUsername()) && password.equals(u.getPassword())){
                return u;
            }
        }
        return null;
    }

    /**
     * Read user list from disk.
     * Note IOException is fail-safe meaning IF the user list does not exist on the disk then a user list is created
     * and saved on the disk.
     * 
     * However, ClassNotFoundException means that the user list on disk is invalid, so an error is raised.
     * @return An empty list if the user list was not created on Disk else the saved UserList.
     */
    private ArrayList<User> fetchUserList() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(this.getUserListLocation()))) {
            return (ArrayList<User>) inputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
            ErrorMessage.showError(ErrorCode.APPERROR, "Invalid user list loaded", "User list was loaded, but the data is corrupt.");
        }
        return new ArrayList<User>();
    }

    /**
     * Write user list to disk
     * @param userList
     * @return True if the user list was successfully written on disk else False.
     */
    private boolean saveUserList(ArrayList<User> userList) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(
                new FileOutputStream(this.getUserListLocation()))) {
            outputStream.writeObject(userList);
        } catch (IOException e) {
            ErrorMessage.showError(ErrorCode.APPERROR, "User cannot be saved.", "User cannot be saved.");
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
