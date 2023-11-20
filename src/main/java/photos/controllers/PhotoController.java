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

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.java.photos.models.Album;
import main.java.photos.models.Photo;
import main.java.photos.models.Tag;
import main.java.photos.utils.ErrorCode;
import main.java.photos.utils.ErrorMessage;

import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javafx.stage.FileChooser;

public class PhotoController extends ParentController implements Initializable {

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
    private TextField addOrRemoveTagTextBox;

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
    private TextField searchDateTextBox;

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
     * TextField to search Tags
     */
    @FXML
    private TextField searchTagTextBox;

    /**
     * Button to make a new album with selection
     */
    @FXML
    private Button makeNewSelectionAlbumButton;

    /**
     * Button to reset filters
     */
    @FXML
    private Button resetFilter;

    /**
     * Space to view a pictures of the album
     */
    @FXML
    private ImageView thumbnail1, thumbnail2, thumbnail3, thumbnail4, thumbnail5,
            thumbnail6, thumbnail7, thumbnail8, thumbnail9, thumbnail10,
            thumbnail11, thumbnail12, thumbnail13, thumbnail14;

    /**
     * main photo we will be looking at
     */
    private Photo chosenPhoto;

    /**
     * constant of photo slots
     */
    public final int NUM_OF_PHOTO_SLOTS = 14;

    /**
     * variable that sets the number of pages of pictures
     * to show in album
     */
    public int page = 0;

    // public ImageView[] thumbnails = {thumbnail1, thumbnail2, thumbnail3,
    // thumbnail4,
    // thumbnail5, thumbnail6, thumbnail7, thumbnail8, thumbnail9,
    // thumbnail10, thumbnail11, thumbnail12, thumbnail13, thumbnail14};

    // public ArrayList<ImageView> thumbnails = new ArrayList<ImageView>(
    // Arrays.asList(thumbnail1, thumbnail2, thumbnail3, thumbnail4,
    // thumbnail5, thumbnail6, thumbnail7, thumbnail8, thumbnail9,
    // thumbnail10, thumbnail11, thumbnail12, thumbnail13, thumbnail14));

    /**
     * Takes user back to albums list
     * 
     * @param event
     */
    @FXML
    private void backToAlbumsList(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/albumView.fxml"));
            Stage stage = new Stage();

            AlbumController albumController = new AlbumController();

            albumController.setActiveUser(getActiveUser());
            albumController.setCurrentStage(stage);
            albumController.setUpdatedUserList(getUpdatedUserList());

            loader.setController(albumController);

            Parent root = loader.load();
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
     * **************NEED TO ADD FUNCTIONALITY TO ADD CAPTION AND TAGS WHEN ADDING
     * PICTURE ****************
     * 
     * @param event
     */
    @FXML
    private void addPhoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg",
                "*.jpeg", "*.gif", "*.bmp");
        fileChooser.getExtensionFilters().add(extFilterJPG);

        File selectedFile = fileChooser.showOpenDialog(this.getCurrentStage());

        Photo addedPhoto = new Photo(this.getActiveUser(), selectedFile);

        if (!this.getActiveUser().getActiveAlbum().duplicatePhoto(addedPhoto)) {
            this.getActiveUser().getActiveAlbum().addPhoto(addedPhoto);
            chosenPhoto = addedPhoto;
            // displayPhoto(addedPhoto, selectedFile, photoViewBox);
        } else {
            ErrorMessage.showError(ErrorCode.AUTHERROR, "Duplicate Photo in Album",
                    "Please choose a different Photo.");
        }

        displayAlbum(this.getActiveUser().getActiveAlbum());
        saveUserToDisk(getActiveUser());
    }

    /**
     * Deletes chosen photo from album
     * 
     * @param event
     */
    @FXML
    private void deletePhoto(ActionEvent event) {
        if (chosenPhoto != null) {
            clearPhoto(thumbnailGetter(this.getActiveUser().getActiveAlbum().getIndexOfPhoto(chosenPhoto)));
            this.getActiveUser().getActiveAlbum().deletePhoto(chosenPhoto);
        } else {
            ErrorMessage.showError(ErrorCode.AUTHERROR, "No Album Chosen",
                    "Please click on an album to delete.");
        }
        chosenPhoto = null;
        displayAlbum(this.getActiveUser().getActiveAlbum());
        saveUserToDisk(getActiveUser());
    }

    /**
     * Function to add or change the caption of a photo
     * 
     * @param event
     */
    @FXML
    private void addOrChangeCaption(ActionEvent event) {
        String caption = addOrChangeCaptionTextBox.getText();
        if (addOrChangeCaptionTextBox.getLength() < 1) {
            ErrorMessage.showError(ErrorCode.AUTHERROR, "Incomplete field",
                    "Please fill in a new Caption.");
        } else {
            chosenPhoto.setCaption(caption);
        }

        displayAlbum(this.getActiveUser().getActiveAlbum());
        saveUserToDisk(getActiveUser());
    }

    /**
     * Function to add a tag to a photo
     * 
     * @param event
     */
    @FXML
    private void addTag(ActionEvent event) {
        String tagString = addOrRemoveTagTextBox.getText();
        if (addOrRemoveTagTextBox.getLength() < 1) {
            ErrorMessage.showError(ErrorCode.AUTHERROR, "Incomplete field",
                    "Please fill in a new Tag.");
        } else {
            Tag tag = new Tag(tagString);
            chosenPhoto.addTag(tag);
        }

        displayAlbum(this.getActiveUser().getActiveAlbum());
        saveUserToDisk(getActiveUser());
    }

    /**
     * Function to remove a tag to a photo
     * 
     * @param event
     */
    @FXML
    private void removeTag(ActionEvent event) {
        String tagString = addOrRemoveTagTextBox.getText();
        if (addOrRemoveTagTextBox.getLength() < 1) {
            ErrorMessage.showError(ErrorCode.AUTHERROR, "Incomplete field",
                    "Please fill in a new Tag.");
        } else {
            Tag tag = new Tag(tagString);
            if (chosenPhoto.findTag(tag) == null) {
                ErrorMessage.showError(ErrorCode.AUTHERROR, "No such Tag found",
                        "Please fill in a new Tag.");
            }
            chosenPhoto.removeTag(chosenPhoto.findTag(tag));
        }

        displayAlbum(this.getActiveUser().getActiveAlbum());
        saveUserToDisk(getActiveUser());
    }

    /**
     * Checks for clicks on grid to see selections
     * 
     * @param event
     */
    @FXML
    public void clickGrid(javafx.scene.input.MouseEvent event) {
        Node clickedNode = event.getPickResult().getIntersectedNode();
        if (clickedNode != albumGridContainer) {
            // click on descendant node
            Integer colIndex = GridPane.getColumnIndex(clickedNode);
            Integer rowIndex = GridPane.getRowIndex(clickedNode);

            if (colIndex == null) {
                colIndex = 0;
            }
            if (rowIndex == null) {
                rowIndex = 0;
            }

            if (getDisplayedPhoto(page, rowIndex, colIndex) != null) {
                chosenPhoto = getDisplayedPhoto(page, rowIndex, colIndex);
            }
            displayAlbum(this.getActiveUser().getActiveAlbum());
            // System.out.println("Mouse clicked cell: " + colIndex + " And: " + rowIndex);
        }
    }

    /**
     * Will show the next photo in album when clicked
     * 
     * @param event
     */
    @FXML
    public void nextPhoto(ActionEvent event) {
        if (this.getActiveUser().getActiveAlbum()
                .getIndexOfPhoto(chosenPhoto) < this.getActiveUser().getActiveAlbum().getNumOfPhotosInAlbum() - 1) {
            chosenPhoto = this.getActiveUser().getActiveAlbum().getNextPhoto(chosenPhoto);
            if (this.getActiveUser().getActiveAlbum().getIndexOfPhoto(chosenPhoto) + 1 > (page + 1)
                    * NUM_OF_PHOTO_SLOTS) {
                page++;
            }
        }
        displayAlbum(this.getActiveUser().getActiveAlbum());
    }

    /**
     * Will show the previous photo in album when clicked
     * 
     * @param event
     */
    @FXML
    public void previousPhoto(ActionEvent event) {
        if (this.getActiveUser().getActiveAlbum().getIndexOfPhoto(chosenPhoto) > 0) {
            int beforeIndex = this.getActiveUser().getActiveAlbum().getIndexOfPhoto(chosenPhoto) + 1;
            chosenPhoto = this.getActiveUser().getActiveAlbum().getPreviousPhoto(chosenPhoto);
            int afterIndex = this.getActiveUser().getActiveAlbum().getIndexOfPhoto(chosenPhoto) + 1;
            if (beforeIndex == (page * NUM_OF_PHOTO_SLOTS + 1) && afterIndex == (page * NUM_OF_PHOTO_SLOTS)
                    && page != 0) {
                page--;
            }
        }
        displayAlbum(this.getActiveUser().getActiveAlbum());
    }

    /**
     * Function to move photo from one album to another
     * 
     * @param event
     */
    @FXML
    public void movePhotoToAnotherAlbum(ActionEvent event) {
        if (chosenPhoto != null) {
            ChoiceDialog<Album> albumsDialog = new ChoiceDialog<Album>();

            ObservableList<Album> albumsList = albumsDialog.getItems();
            albumsList.addAll(this.getActiveUser().getAlbumsList());
            albumsList.remove(this.getActiveUser().getActiveAlbum());
            albumsDialog.setHeaderText("Choose an album to move your picture to");
            albumsDialog.setContentText("Choose your album");

            Optional<Album> result = albumsDialog.showAndWait();
            if (result.isPresent()) {
                result.get().addPhoto(chosenPhoto);
                this.getActiveUser().getActiveAlbum().deletePhoto(chosenPhoto);
                chosenPhoto = null;
            }
        } else {
            ErrorMessage.showError(ErrorCode.AUTHERROR, "No photo chosen",
                    "Please choose a photo to move.");
        }
        displayAlbum(this.getActiveUser().getActiveAlbum());
        saveUserToDisk(getActiveUser());
    }

    /**
     * Function to move photo from one album to another
     * 
     * @param event
     */
    @FXML
    public void copyPhotoToAnotherAlbum(ActionEvent event) {
        if (chosenPhoto != null) {
            ChoiceDialog<Album> albumsDialog = new ChoiceDialog<Album>();

            ObservableList<Album> albumsList = albumsDialog.getItems();
            albumsList.addAll(this.getActiveUser().getAlbumsList());
            albumsList.remove(this.getActiveUser().getActiveAlbum());
            albumsDialog.setHeaderText("Choose an album to copy your picture to");
            albumsDialog.setContentText("Choose your album");

            Optional<Album> result = albumsDialog.showAndWait();
            if (result.isPresent()) {
                result.get().addPhoto(chosenPhoto);
            }
        } else {
            ErrorMessage.showError(ErrorCode.AUTHERROR, "No photo chosen",
                    "Please choose a photo to copy.");
        }
        displayAlbum(this.getActiveUser().getActiveAlbum());
        saveUserToDisk(getActiveUser());
    }

    /**
     * Function to search by date
     * 
     * @param event
     */
    @FXML
    private void searchByDateAction(ActionEvent event) {
        String searchDate = searchDateTextBox.getText();
        if (searchDateTextBox.getLength() < 1) {
            ErrorMessage.showError(ErrorCode.APPERROR, "Incomplete field",
                    "Please fill in a new Date.");
            return;
        }
        Date stringToDate = stringToDate(searchDate);
        if (stringToDate != null) {
            Album dateSearchAlbum = this.getActiveUser().getActiveAlbum().searchByDate(stringToDate);
            displayAlbum(dateSearchAlbum);
        }

    }

    /**
     * Function to search by tag
     * 
     * @param event
     */
    @FXML
    private void searchByTagAction(ActionEvent event) {
        String tagQuery = searchTagTextBox.getText();
        if (searchTagTextBox.getLength() < 1) {
            ErrorMessage.showError(ErrorCode.APPERROR, "Incomplete field",
                    "Please fill in a new Date.");
            return;
        }
        Album tagSearchAlbum = this.getActiveUser().getActiveAlbum().searchByTag(tagQuery);
        if (tagSearchAlbum == null) {
            ErrorMessage.showError(ErrorCode.APPERROR, "Invalid Tag Search Query",
                    "Query format: A=B, A=B OR C=D, A=B AND C=D");
            return;
        }
        displayAlbum(tagSearchAlbum);
    }

    /**
     * Function to show normal album instead of filtered album
     * 
     * @param event
     */
    @FXML
    private void resetFilter(ActionEvent event) {
        searchDateTextBox.clear();
        searchTagTextBox.clear();
        displayAlbum(this.getActiveUser().getActiveAlbum());
    }

    /**
     * **************** NEED TO FIX TO ADD NEW ALBUM NAME
     */
    @FXML
    private void makeNewSelectionAlbum() {
        if (!this.getActiveUser().duplicateAlbumName(this.getActiveUser().getActiveAlbum().getAlbumName())) {
            this.getActiveUser().addAlbum(this.getActiveUser().getActiveAlbum());
        } else {
            ErrorMessage.showError(ErrorCode.AUTHERROR, "Duplicate Album Name",
                    "Please choose another album name.");
        }

    }

    /**
     * Will display album thumbnails
     * 
     * @param album
     */
    private void displayAlbum(Album album) {
        for (int i = 0; i < NUM_OF_PHOTO_SLOTS; i++) {
            if (album.getNumOfPhotosInAlbum() > i + (page * NUM_OF_PHOTO_SLOTS)) {
                if (page > 0) {
                    displayPhoto(album.getPhotosInAlbum().get(i + page * (NUM_OF_PHOTO_SLOTS)),
                            album.getPhotosInAlbum().get(i + page * (NUM_OF_PHOTO_SLOTS)).getFile(),
                            thumbnailGetter(i));
                } else {
                    displayPhoto(album.getPhotosInAlbum().get(i), album.getPhotosInAlbum().get(i).getFile(),
                            thumbnailGetter(i));
                }
            } else {
                clearPhoto(thumbnailGetter(i));
            }
        }

        if (chosenPhoto != null) {
            displayPhoto(chosenPhoto, chosenPhoto.getFile(), photoViewBox);
        } else {
            photoViewBox.setImage(null);
        }
    }

    /**
     * Helper to Display photo in imageView
     * 
     * @param event
     */
    private void displayPhoto(Photo photo, File selectedFile, ImageView imageView) {
        try {
            InputStream stream = new FileInputStream(selectedFile);
            Image image = new Image(stream);

            // Setting image to the image view
            imageView.setImage(image);

            if (photo.equals(chosenPhoto)) {
                captionTextBox.setText(photo.getCaption());
                tagTextBox.setText(photo.setTagsToString());
                dateTextBox.setText(photo.dateToString(photo.getDate()));
            }
        } catch (IOException e) {
            e.printStackTrace();
            ErrorMessage.showError(ErrorCode.APPERROR, "Cannot open photo", e.getMessage());
        }
    }

    /**
     * Helper to clear deleted photos
     * 
     * @param imageView
     */
    private void clearPhoto(ImageView imageView) {
        imageView.setImage(null);
    }

    /**
     * Helper to return the correct imageview
     * 
     * @param index
     * @return
     */
    private ImageView thumbnailGetter(int index) {
        switch (index) {
            case 0:
                return thumbnail1;
            case 1:
                return thumbnail2;
            case 2:
                return thumbnail3;
            case 3:
                return thumbnail4;
            case 4:
                return thumbnail5;
            case 5:
                return thumbnail6;
            case 6:
                return thumbnail7;
            case 7:
                return thumbnail8;
            case 8:
                return thumbnail9;
            case 9:
                return thumbnail10;
            case 10:
                return thumbnail11;
            case 11:
                return thumbnail12;
            case 12:
                return thumbnail13;
            case 13:
                return thumbnail14;
        }
        return null;
    }

    /**
     * Helper to return the correct photo in grid
     */
    public Photo getDisplayedPhoto(int page, int row, int column) {
        int selector = 0;
        selector = NUM_OF_PHOTO_SLOTS * page + row * (NUM_OF_PHOTO_SLOTS / 2) + column;
        if (this.getActiveUser().getActiveAlbum().getPhotosInAlbum().get(selector) != null) {
            return this.getActiveUser().getActiveAlbum().getPhotosInAlbum().get(selector);
        } else {
            return null;
        }
    }

    public Date stringToDate(String dateString) {
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date date;
        try {
            date = format.parse(dateString);
            // System.out.println(date);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            ErrorMessage.showError(ErrorCode.APPERROR, "Cannot convert to date", e.getMessage());
        }
        return null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayAlbum(this.getActiveUser().getActiveAlbum());
    }
}
