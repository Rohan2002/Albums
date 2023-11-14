package main.java.photos.models;

import java.util.ArrayList;

public class Admin 
{
    public ArrayList<User> users;

    public Admin()
    {
        //no arg constructor
    }

    public ArrayList<User> getUsers() {
        return this.users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}
