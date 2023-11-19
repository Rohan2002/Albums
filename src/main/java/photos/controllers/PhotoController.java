/**
 * This class is responsible for the Photo Subsystem.
 * The Photo subsystem can add/delete photos on an album on the application.
 * Added functionality with the power to manipulate the captions of a photo
 * Overall photo viewing and slideshow capabilities
 * 
 * @author Saman Sathenjeri
 * @version 1.0
 */

package main.java.photos.controllers;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.java.photos.models.Album;
import main.java.photos.models.Photo;
import main.java.photos.models.User;
import main.java.photos.models.UserList;
import main.java.photos.utils.ErrorCode;
import main.java.photos.utils.ErrorMessage;
import java.util.ResourceBundle;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import javafx.stage.FileChooser;

public class PhotoController extends ParentController implements Initializable 
{
    /**
     * The album we will be viewing
     */
    private Album album;

    /**
     * Data structure to store the userList.
     */
    private UserList userList;

    /**
     * The album we will be viewing
     */
    private User user;

    /**
     * Button for viewing next picture
     */
    @FXML
    private Button nextPictureButton;

    /**
     * Button for viewing previous picture
     */
    @FXML
    private Button previousPictureButton;

    /**
     * Space to view a singular picture
     */
    @FXML
    private ImageView photoViewBox;

    /**
     * Button to search by date
     */
    @FXML
    private Button searchDateButton;

    /**
     * Text Field to search/remove a tag
     */
    @FXML
    private TextField addOrRemoveOrSearchTagTextBox;

    /**
     * Button to add a tag
     */
    @FXML
    private Button addTagButton;

    /**
     * Button to search by tag
     */
    @FXML
    private Button searchTagButton;

    /**
     * Button to remove a tag
     */
    @FXML
    private Button removeTagButton;

    /**
     * Text Field to change or search date of a photo
     */
    @FXML
    private TextField changeOrSearchDateTextBox;

    /**
     * Button to go back to albums list
     */
    @FXML
    private Button backToAlbumsListButton;

    /**
     * Button to add a photo to album
     */
    @FXML
    private Button addPhotoButton;
    
    /**
     * Button to delete a photo to album
     */
    @FXML
    private Button deletePhotoButton;

    /**
     * Text Field to change or search caption of a photo
     */
    @FXML
    private TextField addOrChangeCaptionTextBox;

    /**
     * Button to add/change a tag of a button
     */
    @FXML
    private Button addOrChangeCaptionButton;

    /**
     * Text Field to display photos date
     */
    @FXML
    private TextField dateTextBox;

    /**
     * Button to copy a photo to album
     */
    @FXML
    private Button copyPhotoToAlbumButton;

    /**
     * Button to move a photo to album
     */
    @FXML
    private Button movePhotoToAlbumButton;

    /**
     * Text Field where caption is displayed
     */
    @FXML
    private TextField captionTextBox;

    /**
     * Text Field where tag is displayed
     */
    @FXML
    private TextField tagTextBox;

    /**
     * Grid container that contains all thumbnails
     */
    @FXML
    private GridPane albumGridContainer;

    /**
     * Space to view a pictures of the album
     */
    @FXML
    private ImageView thumbnail1, thumbnail2, thumbnail3, thumbnail4, thumbnail5,
                        thumbnail6, thumbnail7, thumbnail8, thumbnail9, thumbnail10,
                        thumbnail11, thumbnail12, thumbnail13, thumbnail14;

    /**
     * stage that we bring in
     */
    public Stage stage;

    /**
     * main photo we will be looking at
     */
    private Photo chosenPhoto;

    /**
     * constant of photo slots
     */
    public final int NUM_OF_PHOTO_SLOTS = 14;

    // public ImageView[] thumbnails = {thumbnail1, thumbnail2, thumbnail3, thumbnail4, 
    //                     thumbnail5, thumbnail6, thumbnail7, thumbnail8, thumbnail9, 
    //                     thumbnail10, thumbnail11, thumbnail12, thumbnail13, thumbnail14};

    // public ArrayList<ImageView> thumbnails = new ArrayList<ImageView>(
    //                     Arrays.asList(thumbnail1, thumbnail2, thumbnail3, thumbnail4, 
    //                     thumbnail5, thumbnail6, thumbnail7, thumbnail8, thumbnail9, 
    //                     thumbnail10, thumbnail11, thumbnail12, thumbnail13, thumbnail14));

    /**
     * Takes in the album object to view and manipulate the photos inside
     * @param u
     */
    public void setMainAlbum(User user, Album album, UserList userlist, Stage stage)
    {
        this.user = user;
        this.album = album;
        this.userList = userlist;
        this.stage = stage;
    }

    /**
     * Takes user back to albums list
     * @param event
     */
    @FXML
    private void backToAlbumsList(ActionEvent event)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/albumView.fxml"));

            AlbumController albumController = new AlbumController();
            albumController.setMainUser(user, userList);
            loader.setController(albumController);

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current login window if needed
            ((Stage) backToAlbumsListButton.getScene().getWindow()).close();

        } catch (IOException e) {
            e.printStackTrace();
            ErrorMessage.showError(ErrorCode.APPERROR, "Cannot go back to list of Albums", e.getMessage());
        }
    }

    /**
     * Uses FileChooser to add photo to album
     * 
     * **************NEED TO ADD FUNCTIONALITY TO ADD CAPTION AND TAGS WHEN ADDING PICTURE ****************
     * 
     * @param event
     */
    @FXML
    private void addPhoto(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        
        //fileChooser.setInitialDirectory(new File("data"));
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("JPEG Files", "*.jpeg"),
            new FileChooser.ExtensionFilter("PNG Files", "*.png"),
            new FileChooser.ExtensionFilter("BMP Files", "*.bmp"),
            new FileChooser.ExtensionFilter("GIF Files", "*.gif"));

        File selectedFile = fileChooser.showOpenDialog(stage);

        Photo addedPhoto = new Photo(user, selectedFile);

        if (!album.duplicatePhoto(addedPhoto))
        {
            album.addPhoto(addedPhoto);
            chosenPhoto = addedPhoto;
            //displayPhoto(addedPhoto, selectedFile, photoViewBox);
        }
        else
        {
            ErrorMessage.showError(ErrorCode.AUTHERROR, "Duplicate Photo in Album",
                        "Please choose a different Photo.");
        }

        displayAlbum(album);
    }

    /**
     * Deletes chosen photo from album
     * @param event
     */
    @FXML
    private void deletePhoto(ActionEvent event)
    {
        if (chosenPhoto != null)
        {
            clearPhoto(thumbnailGetter(album.getIndexOfPhoto(chosenPhoto)));
            album.deletePhoto(chosenPhoto);
        }
        else
        {
            ErrorMessage.showError(ErrorCode.AUTHERROR, "No Album Chosen",
                    "Please click on an album to delete.");
        }
        chosenPhoto = null;
        displayAlbum(album);
    }

    /**
     * Function to add or change the caption of a photo
     * @param event
     */
    @FXML
    private void addOrChangeCaption(ActionEvent event)
    {
        String caption = addOrChangeCaptionTextBox.getText();
        if (addOrChangeCaptionTextBox.getLength() < 1) {
            ErrorMessage.showError(ErrorCode.AUTHERROR, "Incomplete field",
                    "Please fill in a new Caption.");
        }
        else
        {
            chosenPhoto.setCaption(caption);
        }

        displayAlbum(album);
    }

    /**
     * Will display album thumbnails
     * 
     * **************NEED TO ADD FUNCTIONALITY TO HANDLE WHEN THERE ARE MORE THAN 14 PHOTOS ****************
     * 
     * @param album
     */
    private void displayAlbum(Album album)
    {
        for (int i = 0; i < NUM_OF_PHOTO_SLOTS; i++)
        {
            if (album.getNumOfPhotosInAlbum() > i)
            {
                displayPhoto(album.getPhotosInAlbum().get(i), album.getPhotosInAlbum().get(i).getFile(), thumbnailGetter(i));
            }
        }
        if (chosenPhoto != null)
        {
            displayPhoto(chosenPhoto, chosenPhoto.getFile(), photoViewBox);
        }
        else
        {
            photoViewBox.setImage(null);
        }
    }

    /**
     * Helper to Display photo in imageView
     * @param event
     */
    private void displayPhoto(Photo photo, File selectedFile, ImageView imageView)
    {
        try 
        {
            InputStream stream = new FileInputStream(selectedFile);
            Image image = new Image(stream);
            
            //Setting image to the image view
            imageView.setImage(image);
            captionTextBox.setText(photo.getCaption());
            //tagTextBox.setText(photo.getAllTags());
            //dateTextBox.setText(photo.getDate());
        }
        catch (IOException e) 
        {
            e.printStackTrace();
            ErrorMessage.showError(ErrorCode.APPERROR, "Cannot open photo", e.getMessage());
        }
    }

    /**
     * Helper to clear deleted photos
     * @param imageView
     */
    private void clearPhoto(ImageView imageView)
    {
        imageView.setImage(null);
    }

    /**
     * Helper to return the correct imageview
     * @param index
     * @return
     */
    private ImageView thumbnailGetter(int index)
    {
        switch (index) 
        {
            case 0: return thumbnail1;
            case 1: return thumbnail2;
            case 2: return thumbnail3;
            case 3: return thumbnail4;
            case 4: return thumbnail5;
            case 5: return thumbnail6;
            case 6: return thumbnail7;
            case 7: return thumbnail8;
            case 8: return thumbnail9;
            case 9: return thumbnail10;
            case 10: return thumbnail11;
            case 11: return thumbnail12;
            case 12: return thumbnail13;
            case 13: return thumbnail14;
        }
        return null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        displayAlbum(album);
    }
}
