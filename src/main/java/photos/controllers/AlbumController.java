/**
 * This class is responsible for the Album Subsystem.
 * The Album subsystem can create, delete and view albums on the application.
 * 
 * @author Saman Sathenjeri, Rohan Deshpande
 * @version 1.0
 */

package main.java.photos.controllers;

import java.io.IOException;
import java.net.URL;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.photos.models.Album;
import main.java.photos.utils.ErrorCode;
import main.java.photos.utils.ErrorMessage;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;

public class AlbumController extends ParentController implements Initializable {
    /**
     * The button to create an Album
     */
    @FXML
    private Button createAlbum;

    /**
     * The button to delete an Album
     */
    @FXML
    private Button deleteAlbum;

    /**
     * The button to rename an Album
     */
    @FXML
    private Button renameAlbum;

    /**
     * The button to open an Album
     */
    @FXML
    private Button openAlbum;

    /**
     * The album chosen by user to view or change
     */
    private Album albumChosen;

    /**
     * Textfield to show the album that is chosen by user
     */
    @FXML
    private TextField albumChosenTextBox;

    /**
     * Textfield to show the number of photos in the album that is chosen by user
     */
    @FXML
    private TextField numOfPhotosOnAlbumTextBox;

    /**
     * Textfield to show the date range of the album that is chosen by user
     */
    @FXML
    private TextField dateRangesTextBox;

    /**
     * Textfield to create a new album
     */
    @FXML
    private TextField createAlbumTextBox;

    /**
     * Textfield to rename an album
     */
    @FXML
    private TextField renameAlbumTextBox;

    /**
     * albumListView is a container for the list of users.
     */
    @FXML
    private ListView<Album> albumList;

    /**
     * Create album with new album name
     * 
     * @param event
     */
    @FXML
    private void createAlbumAction(ActionEvent event) {
        String albumName = createAlbumTextBox.getText();
        if (createAlbumTextBox.getLength() < 1) {
            ErrorMessage.showError(ErrorCode.AUTHERROR, "Incomplete field",
                    "Please fill in a new Album Name.");
        } else {
            if (!this.getActiveUser().duplicateAlbumName(albumName)) {
                Album newAlbum = new Album(albumName);
                this.getActiveUser().addAlbum(newAlbum);
            } else {
                ErrorMessage.showError(ErrorCode.AUTHERROR, "Duplicate Album Name",
                        "Please fill in a different Album Name.");
                return;
            }
        }

        displayList(this.getActiveUser().getAlbumsList());
        saveUserToDisk(this.getActiveUser());
    }

    /**
     * Deletes chosen album
     * 
     * @param event
     */
    @FXML
    private void deleteAlbumAction(ActionEvent event) {
        if (albumChosen != null) {
            this.getActiveUser().deleteAlbum(albumChosen);
        } else {
            ErrorMessage.showError(ErrorCode.AUTHERROR, "No Album Chosen",
                    "Please click on an album to delete.");
        }
        albumChosen = null;
        clearFields();
        displayList(this.getActiveUser().getAlbumsList());
        saveUserToDisk(this.getActiveUser());
    }

    /**
     * Renames chosen Album
     * 
     * @param event
     */
    @FXML
    private void renameAlbumAction(ActionEvent event) {
        String newAlbumName = renameAlbumTextBox.getText();

        if (albumChosen != null) {
            if (renameAlbumTextBox.getLength() < 1) {
                ErrorMessage.showError(ErrorCode.AUTHERROR, "Incomplete field",
                        "Please fill in the new Album Name.");
            } else if (!this.getActiveUser().duplicateAlbumName(newAlbumName)) {
                albumChosen.setAlbumName(newAlbumName);
            } else {
                ErrorMessage.showError(ErrorCode.AUTHERROR, "Duplicate Album Name",
                        "Please fill in a different Album Name.");
            }
        } else {
            ErrorMessage.showError(ErrorCode.AUTHERROR, "No Album Chosen",
                    "Please click on an album to delete.");
        }

        displayList(this.getActiveUser().getAlbumsList());
        saveUserToDisk(this.getActiveUser());
    }

    /**
     * Opens album and sets to photo stage
     * 
     * @param event
     */
    @FXML
    private void openAlbumAction(ActionEvent event) {
        if (albumChosen == null) {
            ErrorMessage.showError(ErrorCode.AUTHERROR, "No Album Chosen",
                    "Please click on an album to delete.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/photoView.fxml"));
            Stage stage = new Stage();
            PhotoController photoController = new PhotoController();

            this.getActiveUser().setActiveAlbum(albumChosen);
            
            photoController.setActiveUser(getActiveUser());
            photoController.setCurrentStage(stage);
            photoController.setUpdatedUserList(getUpdatedUserList());

            loader.setController(photoController);

            Parent root = loader.load();

            stage.setScene(new Scene(root));
            stage.show();

            // Close the current login window if needed
            ((Stage) openAlbum.getScene().getWindow()).close();

        } catch (IOException e) {
            e.printStackTrace();
            ErrorMessage.showError(ErrorCode.APPERROR, "Cannot open Album", e.getMessage());
        }

    }

    /**
     * returns the album that the user is viewing
     * 
     * @param event
     * @return album
     */
    private Album getLiveAlbum(ActionEvent event) {
        albumList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Album>() {

            @Override
            public void changed(ObservableValue<? extends Album> arg0, Album arg1, Album arg2) {
                albumChosen = albumList.getSelectionModel().getSelectedItem();
            }

        });
        albumChosenTextBox.setText(albumChosen.getAlbumName());
        numOfPhotosOnAlbumTextBox.setText(Integer.toString(albumChosen.getNumOfPhotosInAlbum()));
        dateRangesTextBox.setText(albumChosen.getAlbumDateRange());
        return albumChosen;
    }

    /**
     * Routine to update the albums view after add/delete/rename.
     * 
     * @param album
     */
    public void displayList(ArrayList<Album> albums) {
        ObservableList<Album> items = FXCollections.observableArrayList(albums);
        this.albumList.setItems(items);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        albumChosen = null;
        displayList(this.getActiveUser().getAlbumsList());
        albumList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Album>() {

            @Override
            public void changed(ObservableValue<? extends Album> arg0, Album arg1, Album arg2) {
                albumChosen = albumList.getSelectionModel().getSelectedItem();
                updateFields();
            }

        });
    }

    private void updateFields() {
        if (albumChosen == null) {
            albumChosenTextBox.setText("");
            numOfPhotosOnAlbumTextBox.setText("0");
            dateRangesTextBox.setText("N/A");
            return;
        }
        albumChosenTextBox.setText(albumChosen.getAlbumName());
        numOfPhotosOnAlbumTextBox.setText(Integer.toString(albumChosen.getNumOfPhotosInAlbum()));
        dateRangesTextBox.setText(albumChosen.getAlbumDateRange());
    }

    private void clearFields() {
        albumChosenTextBox.clear();
        numOfPhotosOnAlbumTextBox.clear();
        dateRangesTextBox.clear();
    }
}
