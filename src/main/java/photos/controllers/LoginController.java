package main.java.photos.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.java.photos.models.User;
import main.java.photos.models.UserList;
import main.java.photos.utils.ErrorCode;
import main.java.photos.utils.ErrorMessage;
import javafx.scene.control.PasswordField;


public class LoginController {
    private User user;
    private UserList userList;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private void initialize() {
        // Initialize the controller (called after FXML load)
        try{
            UserList userList = new UserList();

            // add stock user
            User stockUser = new User("stock", "stock");  
            if (!userList.userExists(stockUser)){
                userList.addUser(stockUser);
            }

            // add admin user
            User adminUser = new User("admin", "admin");
            if (!userList.userExists(adminUser)){
                userList.addUser(adminUser);
            }

            this.userList = userList;
        }
        catch(IOException e){
            e.printStackTrace();
            ErrorMessage.showError(ErrorCode.APPERROR, "User list error", e.getMessage());
        }
    }

    @FXML
    private void loginAction(ActionEvent event) {
        System.out.println(usernameField);
        if (usernameField.getLength() < 1 || passwordField.getLength() < 1){
            ErrorMessage.showError(ErrorCode.AUTHERROR, "Incomplete auth fields", "Please fill in the username or password.");
        }
        String uname = usernameField.getText().toLowerCase();
        String pword = passwordField.getText();
        
        User logginInUser = new User(uname, pword);
        
        if (this.userList.userExists(logginInUser)){
            System.out.println("Login success!");
        }
        else{
            System.out.println("Login not success!");
        }
    }
}