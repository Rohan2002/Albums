/**
 * This class is responsible for the Album Subsystem.
 * The Album subsystem can create, delete and view albums on the application.
 * 
 * @author Saman Sathenjeri
 * @version 1.0
 */

package main.java.photos.controllers;
import java.io.IOException;

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
import main.java.photos.models.User;
import main.java.photos.models.UserList;
import main.java.photos.utils.ErrorCode;
import main.java.photos.utils.ErrorMessage;
import java.util.ArrayList;

public class AlbumController 
{
    /**
     * Data structure to store the userList.
     */
    private UserList userList;

    /**
     * The user that is viewing their albums
     */
    private User user;

    /**
     * The button to logout user.
     */
    @FXML
    private Button logOut;

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
    private TextField albumChosenName;

    /**
     * Textfield to show the number of photos in the album that is chosen by user
     */
    @FXML
    private TextField albumNumOfPhotos;

    /**
     * Textfield to show the date range of the album that is chosen by user
     */
    @FXML
    private TextField albumDateRange;

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
    private ListView<Album> albumListView;

    @FXML
    private void logOut() {
        // may need to make this an interface/inherited class (used in multiple views)
    }

    /**
     * Create album with new album name
     * @param event
     */
    @FXML
    private void createAlbumAction(ActionEvent event)
    {
        String albumName = createAlbumTextBox.getText();
        if (createAlbumTextBox.getLength() < 1) {
            ErrorMessage.showError(ErrorCode.AUTHERROR, "Incomplete field",
                    "Please fill in the new Album Name.");
        }

        if (!user.duplicateAlbumName(albumName))
        {
            Album newAlbum = new Album(albumName);
            user.addAlbum(newAlbum);
        }
        else
        {
            ErrorMessage.showError(ErrorCode.AUTHERROR, "Duplicate Album Name",
                    "Please fill in a different Album Name.");
        }

        displayList(user.getAlbumsList());
    }

    /**
     * Deletes chosen album
     * @param event
     */
    @FXML
    private void deleteAlbumAction(ActionEvent event)
    {
        if (albumChosen != null)
        {
            user.deleteAlbum(albumChosen);
        }
        else
        {
            ErrorMessage.showError(ErrorCode.AUTHERROR, "No Album Chosen",
                    "Please click on an album to delete.");
        }

        displayList(user.getAlbumsList());
    }

    /**
     * Renames chosen Album
     * @param event
     */
    @FXML
    private void renameAlbumAction(ActionEvent event)
    {
        String newAlbumName = renameAlbumTextBox.getText();

        if (albumChosen != null)
        {
            if (renameAlbumTextBox.getLength() < 1) {
                ErrorMessage.showError(ErrorCode.AUTHERROR, "Incomplete field",
                        "Please fill in the new Album Name.");
            }
            else if (!user.duplicateAlbumName(newAlbumName))
            {
                albumChosen.setAlbumName(newAlbumName);
            }
            else
            {
                ErrorMessage.showError(ErrorCode.AUTHERROR, "Duplicate Album Name",
                        "Please fill in a different Album Name.");
            }
        }
        else
        {
            ErrorMessage.showError(ErrorCode.AUTHERROR, "No Album Chosen",
                    "Please click on an album to delete.");
        }

        displayList(user.getAlbumsList());
    }

    /**
     * Opens album and sets to photo stage
     * @param event
     */
    @FXML
    private void openAlbumAction(ActionEvent event)
    {
        if (albumChosen != null)
        {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/photoView.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

                // Close the current login window if needed
                ((Stage) openAlbum.getScene().getWindow()).close();

            } catch (IOException e) {
                e.printStackTrace();
                ErrorMessage.showError(ErrorCode.APPERROR, "Cannot open Album", e.getMessage());
            }
        }
        else
        {
            ErrorMessage.showError(ErrorCode.AUTHERROR, "No Album Chosen",
                    "Please click on an album to delete.");
        }
    }

    /**
     * returns the album that the user is viewing
     * @param event
     * @return album
     */
    private Album getLiveAlbum(ActionEvent event)
    {
        albumListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Album>() {

            @Override
            public void changed(ObservableValue<? extends Album> arg0, Album arg1, Album arg2) {
                albumChosen = albumListView.getSelectionModel().getSelectedItem();
            }
            
        });
        albumChosenName.setText(albumChosen.getAlbumName());
        albumNumOfPhotos.setText(Integer.toString(albumChosen.getNumOfPhotosInAlbum()));
        albumDateRange.setText(albumChosen.getAlbumDateRange());
        return albumChosen;
    }

    /**
     * Routine to update the albums view after add/delete/rename.
     * @param album
     */
    private void displayList(ArrayList<Album> albums)
    {
        userList.updateUser(user);
        ObservableList<Album> items = FXCollections.observableArrayList(albums);
        this.albumListView.setItems(items);
    }

    @FXML
    private void select(ActionEvent event)
    {
        //nothing
    }

    /**
     * Init user list for app
     */
    @FXML
    private void initialize() {
        try {
            UserList userList = new UserList();
            this.userList = userList;

        } catch (IOException e) {
            e.printStackTrace();
            ErrorMessage.showError(ErrorCode.ADMINERROR, "Cannot load users", e.getMessage());
        }
    }
}
