/**
 * This controller will share the common functions between all controllers.
 * We also created this controller to store the global User, and active Stage (app window).
 * 
 * @author Rohan Deshpande
 * @author Saman Sathenjeri
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
import javafx.stage.Stage;
import main.java.photos.utils.ErrorCode;
import main.java.photos.utils.ErrorMessage;
import main.java.photos.models.User;
import main.java.photos.models.UserList;

public class ParentController {
    /**
     * 
     * Application's global state.
     */
    private User user;

    private UserList userList;

    private Stage stage;

    /**
     * Set the active user.
     * 
     * @param u
     */
    public void setActiveUser(User u) {
        this.user = u;
    }

    /**
     * Get the active user.
     * 
     * @return User
     */
    public User getActiveUser() {
        return this.user;
    }

    /**
     * Set the UserList object.
     * 
     * @param u
     */
    public void setUpdatedUserList(UserList u) {
        this.userList = u;
    }

    /**
     * Get the updated UserList object.
     * 
     * @return Updated UserList
     */
    public UserList getUpdatedUserList() {
        return this.userList;
    }

    /**
     * Set the current active stage.
     * 
     * @param stg Stage
     */
    public void setCurrentStage(Stage stg) {
        this.stage = stg;
    }

    /**
     * Get the current active stage.
     * 
     * @return Stage object.
     */
    public Stage getCurrentStage() {
        return this.stage;
    }

    /**
     * Flush Userlist to disk.
     */
    public void saveUserToDisk(User u){
        if(!this.getUpdatedUserList().updateUser(u)){
            ErrorMessage.showError(ErrorCode.APPERROR, "Could not save user data to disk.", "Could not save user data to disk.");
        }
    }

    /**
     * The button to logout user.
     */
    @FXML
    public Button logOutButton;

    /**
     * Will logout to auth page
     * 
     * @param event
     */
    @FXML
    public void logOut(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/auth.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current login window if needed
            ((Stage) logOutButton.getScene().getWindow()).close();

        } catch (IOException e) {
            e.printStackTrace();
            ErrorMessage.showError(ErrorCode.APPERROR, "Cannot log out user", e.getMessage());
        }
    }
}
