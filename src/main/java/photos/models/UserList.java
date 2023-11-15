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

    public UserList() throws IOException {
        this.userListLocation = Tools.getDataDir().getPath() + File.separator + "ulist.ser";
    }

    public List<User> getUserList() {
        return fetchUserList();
    }

    public String getUserListLocation() {
        return this.userListLocation;
    }

    public boolean userExists(User u) {
        return fetchUserList().contains(u);
    }

    public boolean addUser(User u) {
        ArrayList<User> userList = fetchUserList();
        if (userExists(u)) {
            return false;
        }
        try {
            u.initUser();
        } catch (IOException e) {
            e.printStackTrace();
            ErrorMessage.showError(ErrorCode.AUTHERROR, "User creation error", e.getMessage());
        }
        if (userList.add(u)) {
            return saveUserList(userList);
        }
        ;
        return false;
    }

    public boolean deleteUser(User u) {
        ArrayList<User> userList = fetchUserList();
        if (userList.remove(u)) {
            return saveUserList(userList);
        }
        return false;
    }

    public boolean deleteUser(String usernameToDelete) {
        ArrayList<User> userList = fetchUserList();
        boolean removedUser = false;
        Iterator<User> iterator = userList.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getUsername().equalsIgnoreCase(usernameToDelete)) {
                boolean deleteDirectory = Tools.deleteDirectory(user.getUserDirectory());
                if(!deleteDirectory){
                    return false;
                }
                iterator.remove();
                removedUser = saveUserList(userList);;
            }
        }
        return removedUser;
    }

    private ArrayList<User> fetchUserList() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(this.getUserListLocation()))) {
            return (ArrayList<User>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<User>();
    }

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
