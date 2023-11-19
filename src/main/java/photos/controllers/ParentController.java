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

public class ParentController {
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
