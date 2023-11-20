/**
 * This class is responsible for the Authentication of the user in the
 * application.
 * 
 * @author Rohan Deshpande
 * @version 1.0
 */
package main.java.photos.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.photos.models.User;
import main.java.photos.models.UserList;
import main.java.photos.utils.ErrorCode;
import main.java.photos.utils.ErrorMessage;
import javafx.scene.control.PasswordField;

public class LoginController extends ParentController {
    /**
     * Username field for the user auth.
     */
    @FXML
    private TextField usernameField;

    /**
     * Password field for the user auth.
     */
    @FXML
    private PasswordField passwordField;

    /**
     * Login button for the user auth.
     */
    @FXML
    private Button loginButton;

    public AlbumController albumController = new AlbumController();

    /**
     * Initialize user list with 2 special usernames called stock and admin
     */
    @FXML
    private void initialize() {
        // Initialize the controller (called after FXML load)
        try {
            UserList userList = new UserList();
            // add stock user
            User stockUser = new User("stock", "stock");
            userList.addUser(stockUser);

            // add admin user
            User adminUser = new User("admin", "admin");
            userList.addUser(adminUser);

            this.setUpdatedUserList(userList);
        } catch (IOException e) {
            e.printStackTrace();
            ErrorMessage.showError(ErrorCode.APPERROR, "Cannot load users", e.getMessage());
        }
    }

    /**
     * After successful auth for admin, display the admin subsystem page.
     */
    private void adminView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/adminView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current login window if needed
            ((Stage) loginButton.getScene().getWindow()).close();

        } catch (IOException e) {
            e.printStackTrace();
            ErrorMessage.showError(ErrorCode.APPERROR, "Cannot login as admin", e.getMessage());
        }
    }

    /**
     * After successful auth for non-admin, display the non-admin subsystem page
     * which is the album logic.
     */
    private void albumView(User u) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/albumView.fxml"));
            Stage stage = new Stage();
            AlbumController albumController = new AlbumController();

            albumController.setActiveUser(u);
            albumController.setCurrentStage(stage);
            albumController.setUpdatedUserList(getUpdatedUserList());

            loader.setController(albumController);

            Parent root = loader.load();
            stage.setScene(new Scene(root));

            stage.show();

            // Close the current login window if needed
            ((Stage) loginButton.getScene().getWindow()).close();

        } catch (IOException e) {
            e.printStackTrace();
            ErrorMessage.showError(ErrorCode.APPERROR, "Cannot login as regular user", e.getMessage());
        }
    }

    /**
     * Responsible for checking authentication of the actual user.
     * 
     * @param event
     */
    @FXML
    private void loginAction(ActionEvent event) {
        // System.out.println(usernameField);
        if (usernameField.getLength() < 1 || passwordField.getLength() < 1) {
            ErrorMessage.showError(ErrorCode.AUTHERROR, "Incomplete auth fields",
                    "Please fill in the username or password.");
        }
        String uname = usernameField.getText();
        String pword = passwordField.getText();

        User logginInUser = this.getUpdatedUserList().getUser(uname, pword);
        if (logginInUser != null) {
            if (logginInUser.getUsername().equalsIgnoreCase("admin")) {
                adminView();
            } else {
                albumView(logginInUser);
            }
        } else {
            ErrorMessage.showError(ErrorCode.AUTHERROR, "Invalid credentials!",
                    "Enter the correct username or password!");
        }
    }
}