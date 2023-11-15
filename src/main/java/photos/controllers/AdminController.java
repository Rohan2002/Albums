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

public class AdminController {
    private UserList userList;

    @FXML
    private TextField createUsername;

    @FXML
    private PasswordField createPassword;

    @FXML
    private TextField deleteUsername;

    @FXML
    private Button createUserButton;

    @FXML
    private Button deleteUserButton;

    @FXML
    private Button logOutButton;

    @FXML
    private ListView<User> userListView;

    @FXML
    private void logOut() {
        // may need to make this an interface/inherited class (used in multiple views)
    }

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

    @FXML
    private void createUser(ActionEvent event) {
        String newUsername = createUsername.getText();
        String newPassword = createPassword.getText();

        if (newUsername.length() < 1 || newPassword.length() < 1) {
            ErrorMessage.showError(ErrorCode.ADMINERROR, "Incomplete username or password field!",
                    "Enter a valid username and password!");
        }
        if (!this.userList.addUser(new User(newUsername, newPassword))) {
            ErrorMessage.showError(ErrorCode.ADMINERROR, "Could not add user with username " + newUsername,
                    "Username exists in our database, choose a unique one!");
        }
        updateListView(this.userList);
    }

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

    private void updateListView(UserList u) {
        ObservableList<User> items = FXCollections.observableArrayList(u.getUserList());

        this.userListView.setItems(items);
    }
}