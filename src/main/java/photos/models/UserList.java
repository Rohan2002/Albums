package main.java.photos.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import main.java.photos.utils.ErrorCode;
import main.java.photos.utils.ErrorMessage;
import main.java.photos.utils.Tools;

public class UserList {
    private List<User> userList;
    private String userListLocation;

    public UserList() throws IOException {
        this.userListLocation = Tools.getDataDir().getPath() + File.separator + "ulist.ser";
        this.userList = fetchUserList();
    }

    public String getUserListLocation() {
        return this.userListLocation;
    }

    public boolean userExists(User u) {
        this.userList = fetchUserList();
        return userList.contains(u);
    }

    public boolean addUser(User u) {
        if (userExists(u)) {
            return false;
        }
        try{
            u.initUser();
        }
        catch(IOException e){
            e.printStackTrace();
            ErrorMessage.showError(ErrorCode.AUTHERROR, "User creation error", e.getMessage());
        }
        if(userList.add(u)){
            return this.saveUserList();
        };
        return false;
    }

    public boolean deleteUser(User u) {
        if(userList.remove(u)){
            return this.saveUserList();
        }
        return false;
    }

    private ArrayList<User> fetchUserList() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(this.getUserListLocation()))) {
            return (ArrayList<User>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<User>();
    }

    private boolean saveUserList() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(
                new FileOutputStream(this.getUserListLocation()))) {
            outputStream.writeObject(this.userList);
        } catch (IOException e) {
            ErrorMessage.showError(ErrorCode.APPERROR, "User cannot be saved.", "User cannot be saved.");
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
