package main.java.photos.controllers;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.photos.models.User;
import main.java.photos.models.UserList;
import main.java.photos.utils.ErrorCode;
import main.java.photos.utils.ErrorMessage;

/**
 * This class is responsible for the Admin Subsystem.
 * The Admin subsystem can create, delete and view users on the application.
 * 
 * @author Rohan Deshpande
 * @version 1.0
 */

public class AdminController extends ParentController
{
    /**
     * Data structure to store the userList.
     */
    private UserList userList;

    /**
     * The username field for the create usedr logic.
     */
    @FXML
    private TextField createUsername;

    /**
     * The password field for the create user logic.
     */
    @FXML
    private PasswordField createPassword;

    /**
     * The username field for the delete user logic.
     */
    @FXML
    private TextField deleteUsername;

    /**
     * The button for create user logic.
     */
    @FXML
    private Button createUserButton;

    /**
     * The button for the delete user logic.
     */
    @FXML
    private Button deleteUserButton;

    /**
     * userListView is a container for the list of users.
     */
    @FXML
    private ListView<User> userListView;

    /**
     * Init user list for app and app view.
     */
    @FXML
    private void initialize() {
        try {
            UserList userList = new UserList();
            this.userList = userList;

            updateListView(this.userList);
        } catch (IOException e) {
            e.printStackTrace();
            ErrorMessage.showError(ErrorCode.ADMINERROR, "Cannot load users", e.getMessage());
        }
    }

    /**
     * The create user logic.
     * 
     * The username must not exist in the list of users in order to be added successfully.
     * @param event
     */
    @FXML
    private void createUser(ActionEvent event) {
        String newUsername = createUsername.getText();
        String newPassword = createPassword.getText();

        if (newUsername.length() < 1 || newPassword.length() < 1) {
            ErrorMessage.showError(ErrorCode.ADMINERROR, "Incomplete username or password field!",
                    "Enter a valid username and password!");
            return;
        }
        if (!this.userList.addUser(new User(newUsername, newPassword))) {
            ErrorMessage.showError(ErrorCode.ADMINERROR, "Could not add user with username " + newUsername,
                    "Username exists in our database, choose a unique one!");
            return;
        }
        updateListView(this.userList);
    }
    
    /**
     * The delete user logic.
     * Admin and Stock username as special app username and cannot be deleted via app.
     * The username must exist in the list of users in order to be deleted successfully.
     * @param event
     */
    @FXML
    private void deleteUser(ActionEvent event) {
        String deleteUser = deleteUsername.getText();
        if (deleteUser.equals("admin")) {
            ErrorMessage.showError(ErrorCode.ADMINERROR, "Cannot delete admin user!",
                    "Cannot delete admin user!");
        } else if (deleteUser.equals("stock")) {
            ErrorMessage.showError(ErrorCode.ADMINERROR, "Cannot delete stock user!",
                    "Cannot delete stock user!");
        } else if (deleteUser.length() < 1) {
            ErrorMessage.showError(ErrorCode.ADMINERROR, "Incomplete username field!",
                    "Enter a valid username!");
        } else if (!this.userList.deleteUser(deleteUser)) {
            ErrorMessage.showError(ErrorCode.ADMINERROR, "Could not delete user with username " + deleteUser,
                    "Check if username exists!");
        }
        updateListView(this.userList);
    }

    /**
     * Routine to update the user list view after add/delete.
     * @param u
     */
    private void updateListView(UserList u) {
        ObservableList<User> items = FXCollections.observableArrayList(u.getUserList());

        this.userListView.setItems(items);
    }
}