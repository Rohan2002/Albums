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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.java.photos.models.Album;
import main.java.photos.models.Photo;
import main.java.photos.models.Tag;
import main.java.photos.models.User;
import main.java.photos.models.UserList;
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
import javafx.stage.Modality;

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

    /**
     * variable that sets the number of pages of pictures
     * to show in album
     */
    public int page = 0;

    /**
     * The album that carries the photos that match the filter
     */
    public Album filterAlbum;

    /**
     * The album that we filter was put on
     */
    public Album showAlbum;

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
     * Function to add a tag to a photo
     * @param event
     */
    @FXML
    private void addTag(ActionEvent event)
    {
        String tagString = addOrRemoveTagTextBox.getText();
        if (addOrRemoveTagTextBox.getLength() < 1) {
            ErrorMessage.showError(ErrorCode.AUTHERROR, "Incomplete field",
                    "Please fill in a new Tag.");
        }
        else
        {
            Tag tag = new Tag(tagString);
            chosenPhoto.addTag(tag);
        }

        displayAlbum(album);
    }

    /**
     * Function to remove a tag to a photo
     * @param event
     */
    @FXML
    private void removeTag(ActionEvent event)
    {
        String tagString = addOrRemoveTagTextBox.getText();
        if (addOrRemoveTagTextBox.getLength() < 1) {
            ErrorMessage.showError(ErrorCode.AUTHERROR, "Incomplete field",
                    "Please fill in a new Tag.");
        }
        else
        {
            Tag tag = new Tag(tagString);
            if (chosenPhoto.findTag(tag) == null)
            {
                ErrorMessage.showError(ErrorCode.AUTHERROR, "No such Tag found",
                    "Please fill in a new Tag.");
            }
            chosenPhoto.removeTag(chosenPhoto.findTag(tag));
        }

        displayAlbum(album);
    }

    /**
     * Checks for clicks on grid to see selections
     * @param event 
     */
    @FXML
    public void clickGrid(javafx.scene.input.MouseEvent event) {
        Node clickedNode = event.getPickResult().getIntersectedNode();
        if (clickedNode != albumGridContainer) {
            // click on descendant node
            Integer colIndex = GridPane.getColumnIndex(clickedNode);
            Integer rowIndex = GridPane.getRowIndex(clickedNode);

            if (colIndex == null)
            {
                colIndex = 0;
            }
            if (rowIndex == null)
            {
                rowIndex = 0;
            }
            
            if (getDisplayedPhoto(page, rowIndex, colIndex) != null)
            {
                chosenPhoto = getDisplayedPhoto(page, rowIndex, colIndex);
            }
            displayAlbum(album);
            //System.out.println("Mouse clicked cell: " + colIndex + " And: " + rowIndex);
        }
    }

    /**
     * Will show the next photo in album when clicked
     * @param event
     */
    @FXML
    public void nextPhoto(ActionEvent event)
    {
        if (album.getIndexOfPhoto(chosenPhoto) < album.getNumOfPhotosInAlbum()-1)
        {   
            chosenPhoto = album.getNextPhoto(chosenPhoto);
            if(album.getIndexOfPhoto(chosenPhoto)+1 > (page+1)*NUM_OF_PHOTO_SLOTS)
            {
                page++;
            }
        }
        displayAlbum(album);
    }

    /**
     * Will show the previous photo in album when clicked
     * @param event
     */
    @FXML
    public void previousPhoto(ActionEvent event)
    {
        if (album.getIndexOfPhoto(chosenPhoto) > 0)
        {   
            int beforeIndex = album.getIndexOfPhoto(chosenPhoto)+1;
            chosenPhoto = album.getPreviousPhoto(chosenPhoto);
            int afterIndex = album.getIndexOfPhoto(chosenPhoto)+1;
            if(beforeIndex == (page*NUM_OF_PHOTO_SLOTS+1) && afterIndex == (page*NUM_OF_PHOTO_SLOTS) && page != 0)
            {
                page--;
            }
        }
        displayAlbum(album);
    }

    /**
     * Function to move photo from one album to another
     * @param event
     */
    @FXML
    public void movePhotoToAnotherAlbum(ActionEvent event)
    {
        if (chosenPhoto != null)
        {
            ChoiceDialog<Album> albumsDialog = new ChoiceDialog<Album>();

            ObservableList<Album> albumsList = albumsDialog.getItems();
            albumsList.addAll(user.getAlbumsList());
            albumsList.remove(album);
            albumsDialog.setHeaderText("Choose an album to move your picture to");
            albumsDialog.setContentText("Choose your album");

            Optional<Album> result = albumsDialog.showAndWait();
            if (result.isPresent())
            {
                result.get().addPhoto(chosenPhoto);
                album.deletePhoto(chosenPhoto);
                chosenPhoto = null;
            }
        }
        else
        {
            ErrorMessage.showError(ErrorCode.AUTHERROR, "No photo chosen",
                    "Please choose a photo to move.");
        }
        displayAlbum(album);
    }

    /**
     * Function to move photo from one album to another
     * @param event
     */
    @FXML
    public void copyPhotoToAnotherAlbum(ActionEvent event)
    {
        if (chosenPhoto != null)
        {
            ChoiceDialog<Album> albumsDialog = new ChoiceDialog<Album>();

            ObservableList<Album> albumsList = albumsDialog.getItems();
            albumsList.addAll(user.getAlbumsList());
            albumsList.remove(album);
            albumsDialog.setHeaderText("Choose an album to copy your picture to");
            albumsDialog.setContentText("Choose your album");

            Optional<Album> result = albumsDialog.showAndWait();
            if (result.isPresent())
            {
                result.get().addPhoto(chosenPhoto);
            }
        }
        else
        {
            ErrorMessage.showError(ErrorCode.AUTHERROR, "No photo chosen",
                    "Please choose a photo to copy.");
        }
        displayAlbum(album);
    }

    /**
     * Function to search by date
     * @param event
     */
    @FXML
    private void searchByDateAction(ActionEvent event)
    {
        String searchDate = searchDateTextBox.getText();
        if (searchDateTextBox.getLength() < 1) {
            ErrorMessage.showError(ErrorCode.AUTHERROR, "Incomplete field",
                    "Please fill in a new Date.");
        }
        else if (stringToDate(searchDate) != null)
        {
            filterAlbum = album.searchByDate(stringToDate(searchDate));
        }
        showAlbum = album;
        album = filterAlbum;
        displayAlbum(album);
    }

    /**
     * Function to show normal album instead of filtered album
     * @param event
     */
    @FXML
    private void resetFilter(ActionEvent event)
    {
        album = showAlbum;
        searchDateTextBox.clear();
        searchTagTextBox.clear();
        displayAlbum(album);
    }

    /**
     * **************** NEED TO FIX TO ADD NEW ALBUM NAME
     */
    @FXML
    private void makeNewSelectionAlbum()
    {
        if (!user.duplicateAlbumName(album.getAlbumName()))
        {
            user.addAlbum(album);
        }
        else
        {
            ErrorMessage.showError(ErrorCode.AUTHERROR, "Duplicate Album Name",
                    "Please choose another album name.");
        }
        
    }
        
    /**
     * Will display album thumbnails
     * @param album
     */
    private void displayAlbum(Album album)
    {
        for (int i = 0; i < NUM_OF_PHOTO_SLOTS; i++)
        {
            if (album.getNumOfPhotosInAlbum() > i+(page*NUM_OF_PHOTO_SLOTS))
            {
                if (page > 0)
                {
                    displayPhoto(album.getPhotosInAlbum().get(i+page*(NUM_OF_PHOTO_SLOTS)), album.getPhotosInAlbum().get(i+page*(NUM_OF_PHOTO_SLOTS)).getFile(), thumbnailGetter(i));
                }
                else
                {
                    displayPhoto(album.getPhotosInAlbum().get(i), album.getPhotosInAlbum().get(i).getFile(), thumbnailGetter(i));
                }
            }
            else
            {
                clearPhoto(thumbnailGetter(i));
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

            if (photo.equals(chosenPhoto))
            {
                captionTextBox.setText(photo.getCaption());
                tagTextBox.setText(photo.setTagsToString());
                dateTextBox.setText(photo.dateToString(photo.getDate()));
            }
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

    /**
     * Helper to return the correct photo in grid
     */
    public Photo getDisplayedPhoto(int page, int row, int column)
    {
        int selector = 0;
        selector = NUM_OF_PHOTO_SLOTS*page + row*(NUM_OF_PHOTO_SLOTS/2) + column;
        if (album.getPhotosInAlbum().get(selector) != null)
        {
            return album.getPhotosInAlbum().get(selector);
        }
        else
        {
            return null;
        }
    }

    public Date stringToDate(String dateString)
	{
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
		Date date;
		try 
		{
			date = format.parse(dateString);
            //System.out.println(date);
			return date;
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
			ErrorMessage.showError(ErrorCode.APPERROR, "Cannot convert to date", e.getMessage());
		}
		return null;
	}

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        displayAlbum(album);
    }
}
