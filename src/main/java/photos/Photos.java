/**
 * This class represents the main entry point of our Photo app.
 * This first page that will be displayed is the user authentication.
 * 
 * @author Rohan Deshpande
 * @author Saman Sathenjeri
 * @version 1.0
 */
package main.java.photos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class Photos extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/auth.fxml"));
        Parent root = loader.load();
        
        primaryStage.setTitle("Photos59");
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
