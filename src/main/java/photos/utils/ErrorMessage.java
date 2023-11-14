/**
 * This will trigger a error message in the application if there is to be one.
 * 
 * @author Rohan Deshpande
 * @version 1.0
 */
package main.java.photos.utils;

import javafx.scene.control.Alert;

public class ErrorMessage {
    /**
     * 
     * @param e a value from the enum ErrorCode.
     * @param header title of the error message (short).
     * @param content content of the error message (detailed)
     */
    public static void showError(ErrorCode e, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(e.getErrorCode());
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
