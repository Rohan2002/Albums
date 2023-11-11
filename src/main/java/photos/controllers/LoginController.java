package main.java.photos.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.java.photos.models.User;
import javafx.scene.control.PasswordField;


public class LoginController {
    private User user;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private void initialize() {
        // Initialize the controller (called after FXML load)
        user = new User();
    }

    @FXML
    private void loginAction(ActionEvent event) {
        // Handle button click event
        System.out.println("Button clicked!");
        // You can interact with the model and update the view here
    }
}